package com.bilal.service.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class UserProfile {

    long userId;
    String firstName;
    String lastName;
    Date dob;
    List<PhoneNumber> phoneNumbers;

    Map<Address.Type, Address> addressMap;

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
