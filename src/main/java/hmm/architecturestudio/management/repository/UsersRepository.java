package hmm.architecturestudio.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hmm.architecturestudio.management.model.User;

// Usando extends permitimos la herencia de los métodos básicos necesarios CRUD
// User: nombre de la clase Entity asociada a este repositorio
// Long: tipo de dato del id
public interface UsersRepository extends JpaRepository<User, Long>{

}
