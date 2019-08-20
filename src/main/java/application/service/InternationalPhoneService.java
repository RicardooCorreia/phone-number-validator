package application.service;

import application.model.InternationalPhoneType;
import application.provider.InternationalPhoneProvider;
import application.validator.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternationalPhoneService {

    private final PhoneNumberValidator phoneNumberValidator;
    private final InternationalPhoneProvider internationalPhoneProvider;

    @Autowired
    public InternationalPhoneService(PhoneNumberValidator phoneNumberValidator,
                                     InternationalPhoneProvider internationalPhoneProvider) {
        this.phoneNumberValidator = phoneNumberValidator;
        this.internationalPhoneProvider = internationalPhoneProvider;
    }

    public boolean phoneNumberIsValid(String number) {
        return phoneNumberValidator.isValid(number);
    }

    public Iterable<InternationalPhoneType> getAllPhoneTypes() {
        return internationalPhoneProvider.getAll();
    }
}
