package hmm.architecturestudio.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hmm.architecturestudio.management.model.ProjectType;

public interface ProjectTypesRepository extends JpaRepository<ProjectType, Long> {

    @Query("SELECT pt FROM ProjectType pt WHERE pt.name = :name")
    public Optional<ProjectType> findByName(@Param("name") String name);

}
