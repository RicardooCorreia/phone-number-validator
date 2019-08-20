package application.validator;

import application.provider.InternationalPhoneProvider;
import application.model.InternationalPhoneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RegexPhoneNumberValidator implements PhoneNumberValidator {
    private static final String SPACE = " ";
    private static final String PARENTHESES = "[()]";
    private static final String EMPTY_STRING = "";

    private final InternationalPhoneProvider internationalPhoneProvider;

    @Autowired
    public RegexPhoneNumberValidator(InternationalPhoneProvider internationalPhoneProvider) {
        this.internationalPhoneProvider = internationalPhoneProvider;
    }

    public boolean isValid(String number) {
        if (Objects.isNull(number)) {
            return false;
        }
        String indicative = extractNumberIndicative(number);
        final InternationalPhoneType internationalPhoneType = getInternationalPhoneType(indicative);

        return isValid(number, internationalPhoneType);
    }

    private boolean isValid(String number, InternationalPhoneType internationalPhoneType) {
        return Objects.nonNull(internationalPhoneType) && number.matches(internationalPhoneType.getRegex());
    }

    private InternationalPhoneType getInternationalPhoneType(String indicative) {
        return internationalPhoneProvider.getInternationalPhoneType(indicative).orElse(null);
    }

    private String extractNumberIndicative(String number) {
        return number.split(SPACE)[0].replaceAll(PARENTHESES, EMPTY_STRING);
    }
}
