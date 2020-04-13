package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.model.Role;
import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.RolesRepository;
import hmm.architecturestudio.management.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /*
     * Listing all users
     */
    public List<User> findAll() throws Exception  {
        return this.usersRepository.findAll();
    }

    /*
     * Search User by Id
     */
    public Optional<User> findById(Long id) throws Exception {
        return this.usersRepository.findById(id);
    }
    
    /*
     * Create User
     */
    public User createUser(User user) throws Exception{
    	
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
    
    public User updateUser(User user) throws Exception {

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
    public void deleteById(Long id) throws Exception{

        Optional<User> optionalUser = this.usersRepository.findById(id);

        User user = optionalUser.get();
        
        this.usersRepository.deleteById(id);
    }

}