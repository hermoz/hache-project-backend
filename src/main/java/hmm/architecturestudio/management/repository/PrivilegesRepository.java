package hmm.architecturestudio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hmm.architecturestudio.management.model.Privilege;

public interface PrivilegesRepository extends JpaRepository<Privilege, Long> {

	/**
	 * 
	 * Passing method parameters to the query using named parameters
	 * @param name
	 * @return
	 */
	
    @Query("SELECT p FROM Privilege p WHERE p.name = :name")
    public Optional<Privilege> findByName(@Param("name") String name);

}
