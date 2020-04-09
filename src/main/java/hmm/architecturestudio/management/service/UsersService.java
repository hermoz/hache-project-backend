package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * Decorated as a service and saved on Spring context (register on Spring container)
 *  so it´s injected on the controller
 */
@Service
public class UsersService {

    private UsersRepository usersRepository;

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
        // Save user
        return this.usersRepository.save(user);
    }
    
    /*
     * Update User
     */
    
    public User updateUser(User user) throws Exception {

    	Optional<User> optDestinationUser = usersRepository.findById(user.getId());
        User destinationUser = null;

        // Update the fields
        destinationUser.setUsername(user.getUsername());
        destinationUser.setPassword(user.getPassword());
        destinationUser.setEmail(user.getEmail());
        destinationUser.setName(user.getName());
        destinationUser.setLastname(user.getLastname());
        destinationUser.setPhone(user.getPhone());
        destinationUser.setAddress(user.getAddress());

        // Save user
        return this.usersRepository.save(destinationUser);
    }

    /*
     * Save User
     */
    public User save(User user) {
        return this.usersRepository.save(user);
    }

    /*
     * Delete User by Id
     */
    public void deleteById(Long id) {
        this.usersRepository.deleteById(id);
    }

}