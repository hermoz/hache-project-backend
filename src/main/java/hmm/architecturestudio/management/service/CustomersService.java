package hmm.architecturestudio.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hmm.architecturestudio.management.exception.PrivilegesException;
import hmm.architecturestudio.management.model.Customer;
import hmm.architecturestudio.management.repository.CustomersRepository;
import hmm.architecturestudio.management.util.PrivilegesChecker;

@Service
public class CustomersService {
	
    @Autowired
    private CustomersRepository customersRepository;

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

}
