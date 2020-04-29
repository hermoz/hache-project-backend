package hmm.architecturestudio.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hmm.architecturestudio.management.model.Project;

public interface ProjectsRepository extends JpaRepository<Project, Long> {

}
