package hmm.architecturestudio.management.service;

import hmm.architecturestudio.management.model.User;
import hmm.architecturestudio.management.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return this.usersRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return this.usersRepository.findById(id);
    }

    public User save(User user) {
        return this.usersRepository.save(user);
    }

    public void deleteById(Long id) {
        this.usersRepository.deleteById(id);
    }

}