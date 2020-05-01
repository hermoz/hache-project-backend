package hmm.architecturestudio.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.model.Project;
import hmm.architecturestudio.management.model.ProjectType;
import hmm.architecturestudio.management.repository.ProjectTypesRepository;
import hmm.architecturestudio.management.repository.ProjectsRepository;
import hmm.architecturestudio.management.util.PrivilegesChecker;

@Service
public class ProjectsService {

    @Autowired
    private ProjectTypesRepository projectTypesRepository;
    
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private PrivilegesChecker privilegesChecker;

    public List<ProjectType> getProjectTypes() {
        return this.projectTypesRepository.findAll();
    }
    
    /*
     * Listing all users checking user´s privileges
     * SecurityContextHolder -> to obtain a collection of the currently logged in user's roles.
     * @throws PrivilegesException
     */
    public List<Project> findAll() throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege("READ_PROJECTS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("READ_PROJECTS");
        }

        return this.projectsRepository.findAll();
    }
}
