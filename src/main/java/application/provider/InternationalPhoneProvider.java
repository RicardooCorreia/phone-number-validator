package application.provider;

import application.model.InternationalPhoneType;

import java.util.Optional;

public interface InternationalPhoneProvider {
    Optional<InternationalPhoneType> getInternationalPhoneType(String indicative);
    Iterable<InternationalPhoneType> getAll();
}
