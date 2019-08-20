package application.rest.controller;

import application.model.Customer;
import application.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean savePhoneNumber(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "number") String number) {
        return customerService.savePhoneNumber(name, number);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Customer> getAllCustomers(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "number", required = false, defaultValue = "") String number
    ) {
        return customerService.getAllCustomersBy(name, number);
    }
}
