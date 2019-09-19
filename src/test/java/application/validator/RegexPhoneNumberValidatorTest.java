package application.validator;

import application.model.InternationalPhoneType;
import application.provider.InternationalPhoneProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegexPhoneNumberValidatorTest {

    @Mock
    private InternationalPhoneProvider internationalPhoneProvider;

    @InjectMocks
    private RegexPhoneNumberValidator subject;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldValidateWhenCorrectNumber() {
        // Given
        String number = "(351) 911234567";

        when(internationalPhoneProvider.getInternationalPhoneType("351"))
                .thenReturn(Optional.of(buildInternationalPhone()));

        // When
        final boolean result = subject.isValid(number);

        // Then
        assertTrue(result);
    }

    private InternationalPhoneType buildInternationalPhone() {
        return InternationalPhoneType
                .builder()
                .country("Portugal")
                .indicative("351")
                .regex("\\(351\\)\\ ?\\d{9}$")
                .build();
    }

    @Test
    public void shouldNotValidateWhenInvalidNumber() {
        // Given
        String number = "(251) 9112345678";

        // When
        final boolean result = subject.isValid(number);

        // Then
        assertFalse(result);
    }

    @Test
    public void shouldNotValidateWhenNumberIsNull() {
        // Given
        String number = null;

        // When
        final boolean result = subject.isValid(number);

        // Then
        assertFalse(result);
    }

    @Test
    public void shouldNotValidateWhenIndicativeIsNotValid() {
        // Given
        String number = "(351) 911234567";

        // When
        final boolean result = subject.isValid(number);

        // Then
        assertFalse(result);
    }
}