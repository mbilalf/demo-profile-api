package com.bilal.service.profile.controller;

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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileControllerIntegrationTest {

    @Autowired
    UserProfileController userProfileController;
    private static Long userId = 1l;

    @Test
    public void postAndGetProfile() {
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
        //TODO: Implement equals()
        assertEquals(profile, savedProfile);
    }

    @Test
    public void postPatchAndGetProfile() {
        // TODO: No need to do post again if test methods are sequenced. This makes above test redundant.

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
        JsonResponse response = userProfileController.patchAddress(1l, Address.Type.OFFICE, officeAddress);

        //Assert
        assertTrue(response.getStatus().equals(JsonResponse.JsonResponseStatus.SUCCESS));
    }

    private UserProfile createProfile() {
        Calendar cal = Calendar.getInstance();
        List<Address> addresses = Arrays.asList(
                TestUtils.createAddress(Address.Type.OFFICE, "46 Kippax", "Sydney", "NSW", "2000", "AU"));

        List<PhoneNumber> phoneNumbers = Arrays.asList(TestUtils.createPhoneNumber(PhoneNumber.Type.MOBILE, "098765432"));

        return TestUtils.createProfile("Bilal", "Fazal", cal.getTime(), phoneNumbers, addresses);
    }
}
