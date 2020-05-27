package hmm.architecturestudio.management.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Customer;
import hmm.architecturestudio.management.model.Project;
import hmm.architecturestudio.management.model.ProjectType;
import hmm.architecturestudio.management.repository.CustomersRepository;
import hmm.architecturestudio.management.repository.ProjectTypesRepository;
import hmm.architecturestudio.management.repository.ProjectsRepository;
import hmm.architecturestudio.management.util.Constants;
import hmm.architecturestudio.management.util.PrivilegesChecker;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProjectsService {

    @Autowired
    private ProjectTypesRepository projectTypesRepository;
    
    @Autowired
    private ProjectsRepository projectsRepository;
    
    @Autowired
    private CustomersRepository customersRepository;

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
        if (!privilegesChecker.hasPrivilege(Constants.READ_PROJECTS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_PROJECTS);
        }

        return this.projectsRepository.findAll();
    }
    
    /*
     * Search Project by Id
     */
    public Optional<Project> findById(Long id) throws PrivilegesException {

        if (!privilegesChecker.hasPrivilege(Constants.READ_PROJECTS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_PROJECTS);
        }

        return this.projectsRepository.findById(id);
    }
    
    /**
     * Create Project controlling exceptions (privileges and validations)
     */
    public Project createProject(Project project) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege(Constants.CREATE_PROJECTS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.CREATE_PROJECTS);
        }

        // Check unique fields (like title)
        if (projectsRepository.findByTitle(project.getTitle()).isPresent()) {
            throw new ValidationServiceException("Title in use");
        }

        // We search the project type
        Optional<ProjectType> optionalProjectType = projectTypesRepository.findById(project.getType().getId());
        if (!optionalProjectType.isPresent())
            throw new ValidationServiceException("ProjectType with id " + project.getType().getId() + " does not exists and type is mandatory");

        // We search the customer
        Optional<Customer> optionalCustomer = customersRepository.findById(project.getCustomer().getId());
        if (!optionalCustomer.isPresent())
            throw new ValidationServiceException("Customer with id " + project.getCustomer().getId() + " does not exists and customer is mandatory");


        project.setType(optionalProjectType.get());
        project.setCustomer(optionalCustomer.get());

        // Save project
        return this.projectsRepository.save(project);
    }
    
    /*
     * Update Project
     */
    
    public Project updateProject(Project project) throws PrivilegesException, ValidationServiceException {

    	// Check if user has the privilege
        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_PROJECTS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.UPDATE_PROJECTS);
        }

        if (project.getId() == null) {
            throw new ValidationServiceException("Id field cant be null");
        }
        
        Optional<Project> optDestinationProject = projectsRepository.findById(project.getId());
        Project destinationProject = null;
        if (optDestinationProject.isPresent()) {
        	destinationProject = optDestinationProject.get();
        }  
        else {
        	throw new ValidationServiceException("Project with id " + project.getId() + " not exists");
        }
        
        // Check unique fields (like title)
        if (projectsRepository.findByTitleExcludingID(project.getTitle(), project.getId()).isPresent()) {
            throw new ValidationServiceException("Title in use by other project");
        }
        
        // We search the project type
        Optional<ProjectType> optionalProjectType = projectTypesRepository.findById(project.getType().getId());
        if (!optionalProjectType.isPresent())
            throw new ValidationServiceException("ProjectType with id " + project.getType().getId() + " does not exists and type is mandatory");
        // We search the customer
        Optional<Customer> optionalCustomer = customersRepository.findById(project.getCustomer().getId());
        if (!optionalCustomer.isPresent())
            throw new ValidationServiceException("Customer with id " + project.getCustomer().getId() + " does not exists and customer is mandatory");

        // We update the fields
        destinationProject.setTitle(project.getTitle());
        destinationProject.setLocation(project.getLocation());
        destinationProject.setType(optionalProjectType.get());
        destinationProject.setCustomer(optionalCustomer.get());

        // Save project
        return this.projectsRepository.save(destinationProject);
    }
    
    /*
     * Delete Project by Id
     */
    public void deleteById(Long id) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege(Constants.DELETE_PROJECTS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.DELETE_PROJECTS);
        }

        Optional<Project> optionalProject = this.projectsRepository.findById(id);
        if (!optionalProject.isPresent()) {
            throw new ValidationServiceException("Project id " + id + " does not exists");
        }

        Project project = optionalProject.get();
        this.projectsRepository.deleteById(project.getId());
    }
}
