package application.service;

import application.model.Customer;
import application.repository.CustomerRepository;
import application.validator.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class CustomerService {
    private final PhoneNumberValidator phoneNumberValidator;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(PhoneNumberValidator phoneNumberValidator, CustomerRepository customerRepository) {
        this.phoneNumberValidator = phoneNumberValidator;
        this.customerRepository = customerRepository;
    }

    private int generateId() {
        // hack to generate Id
        // Model @GeneratedValue not working because of missing sequences table
        return new Random().nextInt();
    }

    public boolean savePhoneNumber(String name, String number) {
        if (phoneNumberValidator.isValid(number) && Objects.nonNull(name)) {
            customerRepository.save(Customer
                    .builder()
                    .id(generateId())
                    .name(name)
                    .phone(number)
                    .build());
            return true;
        }
        return false;
    }

    public Iterable<Customer> getAllCustomersBy(String name, String number) {
        return customerRepository.getAllByNameContainsAndPhoneContains(name, number);
    }
}
