package application.model;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternationalPhoneType {
    private String country;
    private String indicative;
    private String regex;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternationalPhoneType that = (InternationalPhoneType) o;
        return Objects.equal(country, that.country) &&
                Objects.equal(indicative, that.indicative) &&
                Objects.equal(regex, that.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country, indicative, regex);
    }
}
