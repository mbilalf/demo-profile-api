package com.bilal.service.profile.utils;

import com.bilal.service.profile.model.UserProfile;
import com.bilal.service.profile.model.UserProfile.Address;
import com.bilal.service.profile.model.UserProfile.PhoneNumber;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility to generate test data
 */
public class TestUtils {

    public static UserProfile createProfile(String firstName, String lastName, LocalDate dob, List<UserProfile.PhoneNumber> phoneNumbers,
                                            List<UserProfile.Address> addresses) {
        //re-arrange address list to map, duplicated will be overwritten, but this seems cool for now
        Map<Address.Type, Address> addressMap = addresses.stream().collect(Collectors.toMap(Address::getType, address -> address));

        UserProfile profile = UserProfile.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dob)
                .phoneNumbers(phoneNumbers)
                .addressMap(new HashMap<>())
                .addressMap(addressMap)
                .build();
        return profile;
    }

    public static Address createAddress(Address.Type type, String addressLine, String city, String state,
                                        String postCode, String country) {
        return Address.builder()
                .addressLine1(addressLine)
                .city(city)
                .state(state)
                .postCode(postCode)
                .country(country)
                .type(type)
                .build();
    }

    public static PhoneNumber createPhoneNumber(PhoneNumber.Type type, String phoneNumber) {
        return PhoneNumber.builder()
                .number(phoneNumber)
                .type(type)
                .build();
    }
}
