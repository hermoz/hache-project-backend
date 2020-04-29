package hmm.architecturestudio.management.repository;

import hmm.architecturestudio.management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customer, Long> {
	
}