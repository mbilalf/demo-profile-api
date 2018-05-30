package com.bilal.service.profile.model;

import com.bilal.service.profile.model.converter.LocalDateDeserializer;
import com.bilal.service.profile.model.converter.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UserProfile {

    private long userId;

    private String firstName;

    private String lastName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    private List<PhoneNumber> phoneNumbers;

    private Map<Address.Type, Address> addressMap;

    @Data
    @AllArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
    public static class PhoneNumber {
        String number;
        Type type;

        public enum Type {
            HOME,
            WORK,
            MOBILE,
            OTHER
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
    public static class Address {
        Type type;
        String addressLine1;
        String addressLine2;
        String city;
        String state;
        String postCode;
        String country;

        public enum Type {
            HOME,
            OFFICE,
            OTHER
        }
    }
}
