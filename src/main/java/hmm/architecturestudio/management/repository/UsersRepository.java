package hmm.architecturestudio.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hmm.architecturestudio.management.model.User;

public interface UsersRepository extends JpaRepository<User, Long>{

}
