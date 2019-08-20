package application.provider;

import application.model.InternationalPhoneType;
import com.google.common.annotations.VisibleForTesting;
import org.simpleflatmapper.csv.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

@Component
@ConditionalOnProperty(name = "phone.numbers.source.type", havingValue = "csv")
public class CsvInternationalPhoneProvider implements InternationalPhoneProvider {

    private static final int COLUMN_NUMBER = 3;

    private final ResourceLoader resourceLoader;
    private final Map<String, InternationalPhoneType> internationalPhoneTypeMap = new HashMap<>();

    @Value("${phone.numbers.source.location}")
    private String csvFilePath;

    @Autowired
    public CsvInternationalPhoneProvider(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void loadCsvFile() throws IOException {
        InputStream inputStream = resourceLoader.getResource(csvFilePath).getInputStream();
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        internationalPhoneTypeMap.putAll(
                CsvParser
                        .stream(reader)
                        .map(this::mapToInternationalPhone)
                        .collect(Collectors.toMap(InternationalPhoneType::getIndicative, Function.identity()))
        );
    }

    private InternationalPhoneType mapToInternationalPhone(String[] columns) {
        checkArgument(columns.length == COLUMN_NUMBER, "Columns size not consistent");
        return new InternationalPhoneType(columns[0], columns[1], columns[2]);
    }

    @Override
    public Optional<InternationalPhoneType> getInternationalPhoneType(String indicative) {
        return Optional.ofNullable(this.internationalPhoneTypeMap.get(indicative));
    }

    @Override
    public Iterable<InternationalPhoneType> getAll() {
        return this.internationalPhoneTypeMap.values();
    }

    @VisibleForTesting
    void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }
}
