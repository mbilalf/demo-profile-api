package com.bilal.service.profile.controller;

import com.bilal.service.profile.exception.NotFoundException;
import com.bilal.service.profile.model.JsonResponse;
import com.bilal.service.profile.model.UserProfile;
import com.bilal.service.profile.model.UserProfile.Address;
import com.bilal.service.profile.model.UserProfile.PhoneNumber;
import com.bilal.service.profile.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileControllerIntegrationTest {

    @Autowired
    UserProfileController userProfileController;
    private static Long userId = 1l;

    /*
     * Note: Underscores in test method names is not unconventional (https://dzone.com/articles/7-popular-unit-test-naming)
     */

    @Test
    public void testPostAndGetProfile_Success() {
        //Prepare test profile
        UserProfile profile = createProfile();

        //Act post
        JsonResponse postResponse = userProfileController.post(userId, profile);

        //Assert
        assertNotNull(postResponse);
        assertTrue(postResponse.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));

        //Act get
        UserProfile savedProfile = userProfileController.get(userId);

        //Assert
        assertNotNull(profile);
        assertEquals(profile, savedProfile);
    }

    @Test
    public void testPatchAddress_Success() {
        // TODO: No need to do post again if test methods are sequenced. Repetition makes some of other test redundant.
        //Prepare test profile
        UserProfile profile = createProfile();
        JsonResponse postResponse = userProfileController.post(userId, profile);
        assertNotNull(postResponse);
        assertTrue(postResponse.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));

        //Get saved profile
        profile = userProfileController.get(userId);
        assertNotNull(profile);
        assertTrue(!CollectionUtils.isEmpty(profile.getAddressMap()));

        //Prepare patch
        Address officeAddress = profile.getAddressMap().values().stream().findAny().get();
        officeAddress.setAddressLine1("201 Sussex Street");

        //Act
        JsonResponse response = userProfileController.patchAddress(userId, Address.Type.OFFICE, officeAddress);

        //Assert
        assertTrue(response.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));
    }

    @Test
    public void testDeleteProfile_Success() {
        // TODO: No need to do post again if test methods are sequenced. Repetition makes some of other test redundant.
        //Prepare test profile
        UserProfile profile = createProfile();
        JsonResponse postResponse = userProfileController.post(userId, profile);
        assertNotNull(postResponse);
        assertTrue(postResponse.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));

        //Get saved profile
        profile = userProfileController.get(userId);
        assertNotNull(profile);
        assertTrue(!CollectionUtils.isEmpty(profile.getAddressMap()));

        //Act
        JsonResponse response = userProfileController.delete(userId);

        //Assert
        assertTrue(response.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));
    }


    @Test
    public void testUpdateProfile_Success() {
        // TODO: No need to do post again if test methods are sequenced. Repetition makes some of other test redundant.
        //Prepare test profile
        UserProfile profile = createProfile();
        JsonResponse postResponse = userProfileController.post(userId, profile);
        assertNotNull(postResponse);
        assertTrue(postResponse.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));

        //Get saved profile
        profile = userProfileController.get(userId);
        assertNotNull(profile);
        assertTrue(!CollectionUtils.isEmpty(profile.getAddressMap()));

        //Update profile
        profile.setPhoneNumbers(Arrays.asList(PhoneNumber.builder().type(PhoneNumber.Type.OTHER).number("0987654321").build()));
        profile.setDateOfBirth(LocalDate.of(1983, 04, 20));

        //Act
        JsonResponse response = userProfileController.put(userId, profile);

        //Assert
        assertTrue(response.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));
    }


    @Test (expected = NotFoundException.class)
    public void testGetProfile_throwsException() {
        userProfileController.get(100l);
    }

    @Test (expected = NotFoundException.class)
    public void testUpdateProfile_throwsException() {

        UserProfile profile = createProfile();
        userProfileController.put(100l, profile);
    }

    @Test (expected = NotFoundException.class)
    public void testPatchAddress_throwsException() {
        Address address = Address.builder().addressLine1("100 Street").city("Sydney").build();
        userProfileController.patchAddress(100l, Address.Type.OTHER, address);
    }

    @Test (expected = NotFoundException.class)
    public void testDeleteProfile_throwsException() {
        userProfileController.delete(100l);
    }

    private UserProfile createProfile() {
        List<Address> addresses = Arrays.asList(
                TestUtils.createAddress(Address.Type.OFFICE, "46 Kippax", "Sydney", "NSW", "2000", "AU"));

        List<PhoneNumber> phoneNumbers = Arrays.asList(TestUtils.createPhoneNumber(PhoneNumber.Type.MOBILE, "098765432"));

        return TestUtils.createProfile("Bilal", "Fazal", LocalDate.of(1980,5,6), phoneNumbers, addresses);
    }
}
