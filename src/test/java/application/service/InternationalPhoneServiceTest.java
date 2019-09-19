package application.service;

import application.model.InternationalPhoneType;
import application.provider.InternationalPhoneProvider;
import application.validator.PhoneNumberValidator;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InternationalPhoneServiceTest {

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @Mock
    private InternationalPhoneProvider internationalPhoneProvider;

    @InjectMocks
    private InternationalPhoneService subject;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldReturnPhoneIsValid() {
        // Given
        String number = "(251) 123456789";
        when(phoneNumberValidator.isValid(number)).thenReturn(true);

        // When
        final boolean result = subject.phoneNumberIsValid(number);

        // Then
        assertTrue(result);
    }

    @Test
    public void shouldReturnPhoneIsInvalid() {
        // Given
        String number = "(null) 123456789";
        when(phoneNumberValidator.isValid(number)).thenReturn(false);

        // When
        final boolean result = subject.phoneNumberIsValid(number);

        // Then
        assertFalse(result);
    }

    @Test
    public void shouldReturnPhoneTypes() {
        // Given
        Iterable<InternationalPhoneType> internationalPhoneTypes = buildInternationalPhoneTypes();
        when(internationalPhoneProvider.getAll()).thenReturn(internationalPhoneTypes);

        // When
        final Iterable<InternationalPhoneType> result = subject.getAllPhoneTypes();

        // Then
        assertThat(result, contains(
                equalTo(buildInternationalPhoneType("Portugal", "351")),
                equalTo(buildInternationalPhoneType("England", "44"))
        ));
    }

    private Iterable<InternationalPhoneType> buildInternationalPhoneTypes() {
        return ImmutableList.of(
                buildInternationalPhoneType("Portugal", "351"),
                buildInternationalPhoneType("England", "44")
        );
    }

    private InternationalPhoneType buildInternationalPhoneType(String country, String indicative) {
        return InternationalPhoneType
                .builder()
                .country(country)
                .indicative(indicative)
                .regex("test regex")
                .build();
    }
}