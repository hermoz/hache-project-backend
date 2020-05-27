package hmm.architecturestudio.management.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Customer;
import hmm.architecturestudio.management.model.Project;
import hmm.architecturestudio.management.repository.CustomersRepository;
import hmm.architecturestudio.management.repository.ProjectsRepository;
import hmm.architecturestudio.management.util.Constants;
import hmm.architecturestudio.management.util.PrivilegesChecker;

@Service
@Transactional(rollbackOn = Exception.class)

//@Transactional annotation is used to tell Spring to roll back transactions if a checked exception occurs
public class CustomersService {
	
    @Autowired
    private CustomersRepository customersRepository;
    
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private PrivilegesChecker privilegesChecker;
    
    public List<Customer> findAll() throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege(Constants.READ_CUSTOMERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_CUSTOMERS);
        }

        return this.customersRepository.findAll();
    }
    
    public Optional<Customer> findById(Long id) throws PrivilegesException {

        if (!privilegesChecker.hasPrivilege(Constants.READ_CUSTOMERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.READ_CUSTOMERS);
        }

        return this.customersRepository.findById(id);
    }
    
    /*
     * Create Customer controlling exceptions (privileges and validations)
     */
    
    public Customer createCustomer(Customer customer) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege(Constants.CREATE_CUSTOMERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.CREATE_CUSTOMERS);
        }

        // Check unique fields (like cif, email, phone)
        if (customersRepository.findByCif(customer.getCif()).isPresent()) {
            throw new ValidationServiceException("Cif in use");
        }

        if (customersRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new ValidationServiceException("Email in use");
        }

        if (customersRepository.findByPhone(customer.getPhone()).isPresent()) {
            throw new ValidationServiceException("Phone in use");
        }

        // We search the projects
        // Collectors.toSet() -> it returns a Collector that accumulates the input elements into a new Set
        Set<Long> projectsIds = new HashSet<>();
        if (customer.getProjects() != null) {
            projectsIds = customer.getProjects().stream().map(p -> p.getId()).collect(Collectors.toSet());    
        }

        List<Project> projects = projectsRepository.findAllById(projectsIds);

        // We assign the projects
        customer.setProjects(projects.stream().collect(Collectors.toSet()));
        // And set each project the customer to have effect
        for (Project project : projects) {
            project.setCustomer(customer);
        }

        // Save customer
        return this.customersRepository.save(customer);
    }
    
    /*
     * Update User
     */
    
    public Customer updateCustomer(Customer customer) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege(Constants.UPDATE_CUSTOMERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.UPDATE_CUSTOMERS);
        }
        
        if (customer.getId() == null) {
            throw new ValidationServiceException("Id field can´t be null");
        }

        Optional<Customer> optDestinationCustomer = customersRepository.findById(customer.getId());
        Customer destinationCustomer = null;
        if (optDestinationCustomer.isPresent()) {
        	destinationCustomer = optDestinationCustomer.get();
        }  
        else {
        	throw new ValidationServiceException("Customer with id " + customer.getId() + " not exists");
        }
            

        // Check unique fields (like cif, email, phone) not exits by other customers
        if (customersRepository.findByCifExcludingID(customer.getCif(), customer.getId()).isPresent()) {
            throw new ValidationServiceException("Cif in use by other customer");
        }

        if (customersRepository.findByEmailExcludingID(customer.getEmail(), customer.getId()).isPresent()) {
            throw new ValidationServiceException("Email in use by other customer");
        }

        if (customersRepository.findByPhoneExcludingID(customer.getPhone(), customer.getId()).isPresent()) {
            throw new ValidationServiceException("Phone in use by other customer");
        }

        // We search the projects
        Set<Long> projectsIds = new HashSet<>();
        if (customer.getProjects() != null) {
            projectsIds = customer.getProjects().stream().map(p -> p.getId()).collect(Collectors.toSet());    
        }
        
        List<Project> projects = projectsRepository.findAllById(projectsIds);

        // We update the fields
        destinationCustomer.setName(customer.getName());
        destinationCustomer.setCif(customer.getCif());
        destinationCustomer.setTaxResidence(customer.getTaxResidence());
        destinationCustomer.setPhone(customer.getPhone());
        destinationCustomer.setPopulation(customer.getPopulation());
        destinationCustomer.setEmail(customer.getEmail());

        // We assign the projects
        customer.setProjects(projects.stream().collect(Collectors.toSet()));
        // And set each project the customer to have effect
        for (Project project : projects) {
            project.setCustomer(customer);
        }

        // Save customer
        return this.customersRepository.save(destinationCustomer);
    }
    
    /*
     * Delete Customer by Id
     */
    
    public void deleteById(Long id) throws PrivilegesException, ValidationServiceException {

    	// Check if user has the privilege
        if (!privilegesChecker.hasPrivilege(Constants.DELETE_CUSTOMERS,
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException(Constants.DELETE_CUSTOMERS);
        }

        Optional<Customer> optionalCustomer = this.customersRepository.findById(id);
        if (!optionalCustomer.isPresent()) {
            throw new ValidationServiceException("Customer id " + id + " does not exists");
        }

        Customer customer = optionalCustomer.get();

        // First, we remove its projects
        this.projectsRepository.deleteAll(customer.getProjects());
        // Then we remove the customer
        this.customersRepository.deleteById(id);
    }

}
