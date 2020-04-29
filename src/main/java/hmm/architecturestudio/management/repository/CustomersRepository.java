package hmm.architecturestudio.management.repository;

import hmm.architecturestudio.management.model.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomersRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.cif = :cif")
    public Optional<Customer> findByCif(@Param("cif") String cif);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    public Optional<Customer> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c WHERE c.phone = :phone")
    public Optional<Customer> findByPhone(@Param("phone") String phone);
    
}