package hmm.architecturestudio.management.service;

import java.util.List;
import java.util.Optional;

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
import hmm.architecturestudio.management.util.PrivilegesChecker;

@Service
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
        if (!privilegesChecker.hasPrivilege("READ_PROJECTS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("READ_PROJECTS");
        }

        return this.projectsRepository.findAll();
    }
    
    /*
     * Search Project by Id
     */
    public Optional<Project> findById(Long id) throws PrivilegesException {

        if (!privilegesChecker.hasPrivilege("READ_PROJECTS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("READ_PROJECTS");
        }

        return this.projectsRepository.findById(id);
    }
    
    /**
     * Create Project controlling exceptions (privileges and validations)
     */
    public Project createProject(Project project) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege("CREATE_PROJECTS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("CREATE_PROJECTS");
        }

        // Check unique fields (like title)
        if (projectsRepository.findByTitle(project.getTitle()).isPresent()) {
            throw new ValidationServiceException("Title in use");
        }

        // We search the project type
        Optional<ProjectType> optionalProjectType = projectTypesRepository.findById(project.getType().getId());
        if (!optionalProjectType.isPresent())
            throw new ValidationServiceException("ProjectType with id " + project.getType().getId() + " does not exists");

        // We search the customer
        Optional<Customer> optionalCustomer = customersRepository.findById(project.getCustomer().getId());
        if (!optionalCustomer.isPresent())
            throw new ValidationServiceException("Customer with id " + project.getCustomer().getId() + " does not exists");


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
        if (!privilegesChecker.hasPrivilege("UPDATE_PROJECTS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("UPDATE_PROJECTS");
        }

        Optional<Project> optDestinationProject = projectsRepository.findById(project.getId());
        Project destinationProject = optDestinationProject.get();


        // We search the project type
        Optional<ProjectType> optionalProjectType = projectTypesRepository.findById(project.getType().getId());
      
        // We search the customer
        Optional<Customer> optionalCustomer = customersRepository.findById(project.getCustomer().getId());
     

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

        if (!privilegesChecker.hasPrivilege("DELETE_PROJECTS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("DELETE_PROJECTS");
        }

        Optional<Project> optionalProject = this.projectsRepository.findById(id);

        Project project = optionalProject.get();
        this.projectsRepository.deleteById(project.getId());
    }
}
