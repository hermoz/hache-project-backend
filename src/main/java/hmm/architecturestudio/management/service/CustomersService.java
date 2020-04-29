package hmm.architecturestudio.management.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.exception.ValidationServiceException;
import hmm.architecturestudio.management.model.Customer;
import hmm.architecturestudio.management.model.Project;
import hmm.architecturestudio.management.repository.CustomersRepository;
import hmm.architecturestudio.management.repository.ProjectsRepository;
import hmm.architecturestudio.management.util.PrivilegesChecker;

@Service
public class CustomersService {
	
    @Autowired
    private CustomersRepository customersRepository;
    
    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private PrivilegesChecker privilegesChecker;
    
    public List<Customer> findAll() throws PrivilegesException {
        if (!privilegesChecker.hasPrivilege("READ_CUSTOMERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("READ_CUSTOMERS");
        }

        return this.customersRepository.findAll();
    }
    
    public Optional<Customer> findById(Long id) throws PrivilegesException {

        if (!privilegesChecker.hasPrivilege("READ_CUSTOMERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("READ_CUSTOMERS");
        }

        return this.customersRepository.findById(id);
    }
    
    /*
     * Create Customer controlling exceptions (privileges and validations)
     */
    
    public Customer createCustomer(Customer customer) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege("CREATE_CUSTOMERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("CREATE_CUSTOMERS");
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
        Set<Long> projectsIds = customer.getProjects().stream().map(p -> p.getId()).collect(Collectors.toSet());
        List<Project> projects = projectsRepository.findAllById(projectsIds);

        // We assign the projects
        customer.setProjects(projects.stream().collect(Collectors.toSet()));

        // Save customer
        return this.customersRepository.save(customer);
    }
    
    /*
     * Update User
     */
    
    public Customer updateCustomer(Customer customer) throws PrivilegesException, ValidationServiceException {

        if (!privilegesChecker.hasPrivilege("UPDATE_CUSTOMERS",
                SecurityContextHolder.getContext().getAuthentication().getAuthorities())
        )
        {
            throw new PrivilegesException("UPDATE_CUSTOMERS");
        }

        Optional<Customer> optDestinationCustomer = customersRepository.findById(customer.getId());
        Customer destinationCustomer = optDestinationCustomer.get();

        // Check unique fields (like cif, email, phone) not exits

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
        Set<Long> projectsIds = customer.getProjects().stream().map(p -> p.getId()).collect(Collectors.toSet());
        List<Project> projects = projectsRepository.findAllById(projectsIds);

        // We update the fields
        destinationCustomer.setName(customer.getName());
        destinationCustomer.setCif(customer.getCif());
        destinationCustomer.setTaxResidence(customer.getTaxResidence());
        destinationCustomer.setPhone(customer.getPhone());
        destinationCustomer.setPopulation(customer.getPopulation());
        destinationCustomer.setEmail(customer.getEmail());

        customer.setProjects(projects.stream().collect(Collectors.toSet()));

        // Save customer
        return this.customersRepository.save(destinationCustomer);
    }

}
