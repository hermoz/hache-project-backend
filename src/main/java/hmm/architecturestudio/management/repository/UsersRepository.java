package hmm.architecturestudio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hmm.architecturestudio.management.model.User;

// Usando extends permitimos la herencia de los métodos básicos necesarios CRUD
// User: nombre de la clase Entity asociada a este repositorio
// Long: tipo de dato del id
public interface UsersRepository extends JpaRepository<User, Long>{

	/*
	 * Definition of query and parameter
	 */
	@Query("SELECT u FROM User u WHERE (u.username) = :username")
    public Optional<User> findByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.phone = :phone")
    public Optional<User> findByPhone(@Param("phone") String phone);
    
}
