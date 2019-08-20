package application.repository;

import application.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Iterable<Customer> getAllByNameContains(String name);

    Iterable<Customer> getAllByPhoneContains(String phone);

    Iterable<Customer> getAllByNameContainsAndPhoneContains(String name, String phone);

    Iterable<Customer> getAllByPhoneStartsWith(String indicative);
}
