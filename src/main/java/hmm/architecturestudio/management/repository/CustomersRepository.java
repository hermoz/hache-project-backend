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
    
    // Declared queries to check unique fields don´t exist by other customers
    // so we exclude id on the query
    @Query("SELECT c FROM Customer c WHERE c.cif = :cif and c.id <> :id")
    public Optional<Customer> findByCifExcludingID(@Param("cif") String cif, @Param("id") Long id);

    @Query("SELECT c FROM Customer c WHERE c.email = :email and c.id <> :id")
    public Optional<Customer> findByEmailExcludingID(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT c FROM Customer c WHERE c.phone = :phone and c.id <> :id")
    public Optional<Customer> findByPhoneExcludingID(@Param("phone") String phone, @Param("id") Long id);
    
}