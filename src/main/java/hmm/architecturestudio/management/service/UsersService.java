package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Decorated as a service and saved on Spring context (register on Spring container)
 *  so it´s injected on the controller
 */
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /*
     * Listing all users
     */
    public List<User> findAll() {
        return this.usersRepository.findAll();
    }

    /*
     * Search User by Id
     */
    public Optional<User> findById(Long id) {
        return this.usersRepository.findById(id);
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