package application.rest.controller;

import application.model.InternationalPhoneType;
import application.service.InternationalPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class InternationalPhoneController {
    private final InternationalPhoneService internationalPhoneService;

    @Autowired
    public InternationalPhoneController(InternationalPhoneService internationalPhoneService) {
        this.internationalPhoneService = internationalPhoneService;
    }

    @RequestMapping(value = "/validate/{number}", method = RequestMethod.GET)
    public boolean phoneNumberIsValid(@PathVariable String number) {
        return internationalPhoneService.phoneNumberIsValid(number);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<InternationalPhoneType> getAllPhoneTypes() {
        return internationalPhoneService.getAllPhoneTypes();
    }
}
