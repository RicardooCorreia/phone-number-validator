package application.model;

import com.google.common.base.Objects;

public class InternationalPhoneType {
    private String country;
    private String indicative;
    private String regex;

    public InternationalPhoneType(String country, String indicative, String regex) {
        this.country = country;
        this.indicative = indicative;
        this.regex = regex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndicative() {
        return indicative;
    }

    public void setIndicative(String indicative) {
        this.indicative = indicative;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

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

    public static final class Builder {
        private String country;
        private String indicative;
        private String regex;

        private Builder() {
        }

        public static Builder anInternationalPhoneType() {
            return new Builder();
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withIndicative(String indicative) {
            this.indicative = indicative;
            return this;
        }

        public Builder withRegex(String regex) {
            this.regex = regex;
            return this;
        }

        public InternationalPhoneType build() {
            return new InternationalPhoneType(country, indicative, regex);
        }
    }
}
