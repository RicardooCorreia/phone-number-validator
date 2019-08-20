package application.provider;

import application.model.InternationalPhoneType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class CsvInternationalPhoneProviderTest {

    private CsvInternationalPhoneProvider subject;

    @Before
    public void setUp() throws Exception {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        subject = new CsvInternationalPhoneProvider(resourceLoader);
    }

    @Test
    public void shouldReturnWhenIndicativeIsFound() throws IOException {
        // Given
        loadFile("/phone-numbers-test.csv");

        // When
        Optional<InternationalPhoneType> internationalPhoneType = subject.getInternationalPhoneType("256");

        // Then
        assertTrue(internationalPhoneType.isPresent());
        assertThat(internationalPhoneType.get(), allOf(
                hasProperty("country", equalTo("Uganda")),
                hasProperty("indicative", equalTo("256")),
                hasProperty("regex", equalTo("\\(256\\)\\ ?\\d{9}$"))

        ));
    }

    @Test
    public void shouldReturnEmptyWhenIndicativeIsNotFound() throws IOException {
        // Given
        loadFile("/phone-numbers-test.csv");

        // When
        Optional<InternationalPhoneType> internationalPhoneType = subject.getInternationalPhoneType("351");

        // Then
        assertFalse(internationalPhoneType.isPresent());
    }

    @Test
    public void shouldReturnAllPhoneTypes() throws IOException {
        // Given
        loadFile("/phone-numbers-test.csv");

        // When
        final Iterable<InternationalPhoneType> result = subject.getAll();

        // Then
        assertThat(result, contains(
                equalTo(buildInternationalPhoneType("Mozambique", "258", "\\(258\\)\\ ?[28]\\d{7,8}$")),
                equalTo(buildInternationalPhoneType("Uganda", "256", "\\(256\\)\\ ?\\d{9}$"))
        ));
    }

    private InternationalPhoneType buildInternationalPhoneType(String country, String indicative, String regex) {
        return InternationalPhoneType.Builder
                .anInternationalPhoneType()
                .withCountry(country)
                .withIndicative(indicative)
                .withRegex(regex)
                .build();
    }

    private void loadFile(String filePath) throws IOException {
        subject.setCsvFilePath(filePath);
        subject.loadCsvFile();
    }
}