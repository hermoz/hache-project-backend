package hmm.architecturestudio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hmm.architecturestudio.management.model.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {

	/**
	 * 
	 * Passing method parameters to the query using named parameters
	 * @param name
	 * @return
	 */
	
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    public Optional<Role> findByName(@Param("name") String name);

}