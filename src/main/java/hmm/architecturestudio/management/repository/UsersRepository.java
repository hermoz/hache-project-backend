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
    
    // Declared queries to check unique fields don´t exist by other customers
    // so we exclude id on the query
    @Query("SELECT u FROM User u WHERE u.username = :username and u.id <> :id")
    public Optional<User> findByUsernameExcludingID(@Param("username") String username, @Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email and u.id <> :id")
    public Optional<User> findByEmailExcludingID(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.phone = :phone and u.id <> :id")
    public Optional<User> findByPhoneExcludingID(@Param("phone") String phone, @Param("id") Long id);
    
}
