package application.service;

import application.model.Customer;
import application.repository.CustomerRepository;
import application.validator.PhoneNumberValidator;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerServiceTest {

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService subject;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldSaveCustomerWhenPhoneIsValid() {
        // Given
        String phoneNumber = "123456789";
        String name = "Test Case";

        when(phoneNumberValidator.isValid(phoneNumber)).thenReturn(true);

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        // When
        subject.savePhoneNumber(name, phoneNumber);
        verify(customerRepository).save(argumentCaptor.capture());

        // Then
        Customer customer = argumentCaptor.getValue();
        assertThat(customer, allOf(
                hasProperty("name", equalTo(name)),
                hasProperty("phone", equalTo(phoneNumber)),
                hasProperty("id", notNullValue())
        ));
    }

    @Test
    public void shouldNotSaveWhenPhoneIsInvalid() {
        // Given
        String phoneNumber = "123456789";
        String name = "Test Case";

        when(phoneNumberValidator.isValid(phoneNumber)).thenReturn(false);

        // When
        final boolean result = subject.savePhoneNumber(name, phoneNumber);

        // Then
        assertFalse(result);
    }

    @Test
    public void shouldNotSaveWhenNameIsNull() {
        // Given
        String phoneNumber = "123456789";
        String name = null;

        when(phoneNumberValidator.isValid(phoneNumber)).thenReturn(true);

        // When
        final boolean result = subject.savePhoneNumber(name, phoneNumber);

        // Then
        assertFalse(result);
    }

    @Test
    public void shouldReturnAllCustomers() {
        // Given
        Iterable<Customer> customers = buildCustomers();

        when(customerRepository.getAllByNameContainsAndPhoneContains(anyString(), anyString())).thenReturn(customers);

        // When
        final Iterable<Customer> result = subject.getAllCustomersBy("Test", "3");

        // Then
        assertThat(result, contains(
                equalTo(buildCustomer(1, "Test 1", "123")),
                equalTo(buildCustomer(2, "Test 2", "234"))
        ));
    }

    private Iterable<Customer> buildCustomers() {
        return ImmutableList.of(
                buildCustomer(1, "Test 1", "123"),
                buildCustomer(2, "Test 2", "234")
        );
    }

    private Customer buildCustomer(int id, String name, String number) {
        return Customer.Builder
                .aCustomer()
                .withId(id)
                .withName(name)
                .withPhone(number)
                .build();
    }
}