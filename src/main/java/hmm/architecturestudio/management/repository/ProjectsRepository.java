package hmm.architecturestudio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hmm.architecturestudio.management.model.Project;

public interface ProjectsRepository extends JpaRepository<Project, Long> {

	 @Query("SELECT p FROM Project p WHERE p.title = :title")
	    public Optional<Project> findByTitle(@Param("title") String title);
}
