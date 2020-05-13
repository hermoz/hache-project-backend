package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Role;
import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.RolesRepository;
import hmm.architecturestudio.management.repository.UsersRepository;
import hmm.architecturestudio.management.util.PrivilegesChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Decorated as a service and saved on Spring context (register on Spring container)
 *  so it´s injected on the controller
 */
@Service
public class UsersService {

	@Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PrivilegesChecker privilegesChecker;
    
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /*
     * Listing all users checking user´s privileges
     * SecurityContextHolder -> to obtain a collection of the currently logged in user's roles.
     * @throws PrivilegesException
     */

    public List<User> findAll() throws PrivilegesException {
    	
    	if (!privilegesChecker.hasPrivilege("READ_USERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
    		throw new PrivilegesException("READ_USERS");
        }
    	
    	return this.usersRepository.findAll();
        
    }

    /*
     * Search User by Id
     */
    public Optional<User> findById(Long id) throws PrivilegesException{
    	
    	if (!privilegesChecker.hasPrivilege("READ_USERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
    		throw new PrivilegesException("READ_USERS");
        }
    	
        return this.usersRepository.findById(id);
    }
    
    //***************************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * We need to put more claims of the user in the jwt token because of error on the frontend
     * once de user is authenticated
     * @param username
     * @param checkSecurity
     * @return
     * @throws PrivilegesException
     */
    public Optional<User> findByUsername(String username, boolean checkSecurity) throws PrivilegesException {

        if (checkSecurity && !privilegesChecker.hasPrivilege("READ_USERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("READ_USERS");
        }

        return this.usersRepository.findByUsername(username);
    }
    
    /*
     * Create User controlling exceptions (privileges and validations)
     */
    public User createUser(User user) throws PrivilegesException, ValidationServiceException {

    	// Check if user has the privilege
    	if (!privilegesChecker.hasPrivilege("CREATE_USERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
    		throw new PrivilegesException("CREATE_USERS");
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
        Set<Long> rolesIds = user.getRoles().stream().map(u -> u.getId()).collect(Collectors.toSet());
        List<Role> roles = rolesRepository.findAllById(rolesIds);

        // We assign the roles
        user.setRoles(roles.stream().collect(Collectors.toSet()));
        
        // Save user
        return this.usersRepository.save(user);
    }
    
    /*
     * Update User
     */
    
    public User updateUser(User user) throws PrivilegesException {

    	// Check if user has the privilege
    	if (!privilegesChecker.hasPrivilege("UPDATE_USERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
    		throw new PrivilegesException("UPDATE_USERS");
        }
    	
    	Optional<User> optDestinationUser = usersRepository.findById(user.getId());
        User destinationUser = optDestinationUser.get();


        
        // Search the roles
        Set<Long> rolesIds = user.getRoles().stream().map(u -> u.getId()).collect(Collectors.toSet());
        List<Role> roles = rolesRepository.findAllById(rolesIds);
        
        // Update the fields
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
    public void deleteById(Long id) throws PrivilegesException{

    	// Check if user has the privilege
    	if (!privilegesChecker.hasPrivilege("DELETE_USERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
    		throw new PrivilegesException("DELETE_USERS");
        }
    	
        Optional<User> optionalUser = this.usersRepository.findById(id);

        User user = optionalUser.get();
        
        user.setRoles(null);
        this.usersRepository.deleteById(id);
    }

}