package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Role;
import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.RolesRepository;
import hmm.architecturestudio.management.repository.UsersRepository;
import hmm.architecturestudio.management.util.Constants;
import hmm.architecturestudio.management.util.PrivilegesChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

/*
 * Decorated as a service and saved on Spring context (register on Spring container)
 *  so it´s injected on the controller
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UsersService {

	@Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PrivilegesChecker privilegesChecker;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Listing all users checking user´s privileges
     * SecurityContextHolder -> to obtain a collection of the currently logged in user's roles.
     * @throws PrivilegesException
     */

    public List<User> findAll() throws PrivilegesException {
    	
        if (!privilegesChecker.hasPrivilege(Constants.READ_USERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_USERS);
        }

        return this.usersRepository.findAll();
    }

    /*
     * Search User by Id
     */
    public Optional<User> findById(Long id) throws PrivilegesException{
    	
        if (!privilegesChecker.hasPrivilege(Constants.READ_USERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_USERS);
        }

        return this.usersRepository.findById(id);
    }
    
    /**
     * We need to put more claims of the user in the jwt token because of error on the frontend
     * once de user is authenticated
     * @param username
     * @param checkSecurity
     * @return
     * @throws PrivilegesException
     */
    public Optional<User> findByUsername(String username, boolean checkSecurity) throws PrivilegesException {

        if (checkSecurity && !privilegesChecker.hasPrivilege(Constants.READ_USERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_USERS);
        }

        return this.usersRepository.findByUsername(username);
    }
    
    /*
     * Create User controlling exceptions (privileges and validations)
     */
    public User createUser(User user) throws PrivilegesException, ValidationServiceException {

    	// Check if user has the privilege
        if (!privilegesChecker.hasPrivilege(Constants.CREATE_USERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.CREATE_USERS);
        }
    	
    	// TODO: Check mandatory fields
    	// In this moment we only check the password as mandatory
    	if (user.getPassword() == null || user.getPassword().isEmpty()) {
    	    throw new ValidationServiceException("Password is mandatory");
    	}
    	
    	// Check unique fields (like username, email, phone)
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationServiceException("Username in use");
        }

        if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationServiceException("Email in use");
        }

        if (usersRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new ValidationServiceException("Phone in use");
        }
    	
    	// We search the roles
    	// Collectors.toSet() -> it returns a Collector that accumulates the input elements into a new Set
        Set<Long> rolesIds = new HashSet<>();
        if (user.getRoles() != null) {
            rolesIds = user.getRoles().stream().map(u -> u.getId()).collect(Collectors.toSet());    
        }

        List<Role> roles = rolesRepository.findAllById(rolesIds);
        
        // We assign the roles
        user.setRoles(roles.stream().collect(Collectors.toSet()));
        
        // We encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Save user
        return this.usersRepository.save(user);
    }
    
    /*
     * Update User
     */
    
    public User updateUser(User user) throws PrivilegesException, ValidationServiceException {

    	// Check if user has the privilege
        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_USERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.UPDATE_USERS);
        }
    	
        if (user.getId() == null) {
            throw new ValidationServiceException("Id field cant be null");
        }
        
        Optional<User> optDestinationUser = usersRepository.findById(user.getId());
        User destinationUser = null;
        if (optDestinationUser.isPresent()) {
        	destinationUser = optDestinationUser.get();
        }
        else {
        	throw new ValidationServiceException("User with id " + user.getId() + " not exists");
        }
            
        // Check unique fields (like username, email, phone) not exists by other user
        if (usersRepository.findByUsernameExcludingID(user.getUsername(), user.getId()).isPresent()) {
            throw new ValidationServiceException("Username in use by other user");
        }

        if (usersRepository.findByEmailExcludingID(user.getEmail(), user.getId()).isPresent()) {
            throw new ValidationServiceException("Email in use by other user");
        }

        if (usersRepository.findByPhoneExcludingID(user.getPhone(), user.getId()).isPresent()) {
            throw new ValidationServiceException("Phone in use by other user");
        }

        
        // Search the roles
        Set<Long> rolesIds = new HashSet<>();
        if (user.getRoles() != null) {
            rolesIds = user.getRoles().stream().map(u -> u.getId()).collect(Collectors.toSet());
        }
        List<Role> roles = rolesRepository.findAllById(rolesIds);
        
        // We update the fields
        destinationUser.setUsername(user.getUsername());
        
        if (user.getPassword() != null) {
        	destinationUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        	
        
        destinationUser.setUsername(user.getUsername());
        destinationUser.setPassword(user.getPassword());
        destinationUser.setEmail(user.getEmail());
        destinationUser.setName(user.getName());
        destinationUser.setLastname(user.getLastname());
        destinationUser.setPhone(user.getPhone());
        destinationUser.setAddress(user.getAddress());
        
        destinationUser.setRoles(roles.stream().collect(Collectors.toSet()));

        // Save user
        return this.usersRepository.save(destinationUser);
    }

    /*
     * Delete User by Id
     */
    public void deleteById(Long id) throws PrivilegesException, ValidationServiceException{

    	// Check if user has the privilege
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_USERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.DELETE_USERS);
        }
    	
        Optional<User> optionalUser = this.usersRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ValidationServiceException("User id " + id + " does not exists");
        }

        User user = optionalUser.get();
        
        user.setRoles(null);
        this.usersRepository.deleteById(id);
    }

}