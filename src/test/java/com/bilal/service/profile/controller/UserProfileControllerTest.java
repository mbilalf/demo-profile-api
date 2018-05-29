package com.bilal.service.profile.controller;

import com.bilal.service.profile.exception.NotFoundException;
import com.bilal.service.profile.model.JsonResponse;
import com.bilal.service.profile.model.UserProfile;
import com.bilal.service.profile.model.UserProfile.Address;
import com.bilal.service.profile.service.ProfileService;
import com.bilal.service.profile.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProfileControllerTest {

    @InjectMocks
    UserProfileController userProfileController;

    @Mock
    ProfileService profileService;

    private MockMvc mockMvc;

    private String baseUrl = "/api/v1/profiles";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
    }

    @Test
    public void testGetProfile() throws Exception {
        //Arrange
        String getUserUrl = baseUrl + "/123";
        when(profileService.getProfile(any(Long.class))).thenReturn(getTestProfile());

        //Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(getUserUrl));

        //Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testPostProfileSuccess() throws Exception {
        //Arrange
        String postUserUrl = baseUrl + "/123";
        when(profileService.saveProfile(any(Long.class), any(UserProfile.class))).thenReturn(getTestProfile());
        UserProfile profile = getTestProfile();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(profile);

        //Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(postUserUrl)
                        .content(json)
                        .contentType("application/json"));

        //Assert
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }


    @Test
    public void testPutProfileSuccess() throws Exception {
        //Arrange
        String putUserUrl = baseUrl + "/123";
        when(profileService.saveProfile(any(Long.class), any(UserProfile.class))).thenReturn(getTestProfile());
        UserProfile profile = getTestProfile();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(profile);

        //Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .put(putUserUrl)
                        .content(json)
                        .contentType("application/json"));

        //Assert
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testPatchAddressSuccess() throws Exception {
        //Arrange
        Long userId = 123l;
        String patchAddressUrl = baseUrl + String.format("/%s/address/OFFICE", userId);
        Address newOfficeAddress = TestUtils.createAddress(UserProfile.Address.Type.OFFICE, "201 Sussex Street", "Sydney",
                "NSW", "2000", "AU");
        when(profileService.updateProfileAddress(eq(123l), eq(UserProfile.Address.Type.OFFICE), eq(newOfficeAddress)))
                .thenReturn(newOfficeAddress);
        String addressJson = new ObjectMapper().writeValueAsString(newOfficeAddress);

        //Act
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(patchAddressUrl)
                        .content(addressJson)
                        .contentType("application/json"));

        //Assert
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void testPatchAddressError() throws Exception {
        //Arrange
        Long userId = 123l;
        String patchAddressUrl = baseUrl + String.format("/%s/address/OFFICE", userId);
        Address newOfficeAddress = TestUtils.createAddress(UserProfile.Address.Type.OFFICE, "201 Sussex Street", "Sydney",
                "NSW", "2000", "AU");

        when(profileService.updateProfileAddress(eq(123l), eq(UserProfile.Address.Type.OFFICE), eq(newOfficeAddress)))
                .thenThrow(new NotFoundException());

        //Act
        String addressJson = new ObjectMapper().writeValueAsString(newOfficeAddress);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(patchAddressUrl)
                        .content(addressJson)
                        .contentType("application/json"));

        //Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void testDeleteProfile() throws Exception {
        //Arrange
        String deleteUrl = baseUrl + "/123";
        when(profileService.removeProfile(any(Long.class))).thenReturn(true);

        //Act
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(deleteUrl));

        //Assert
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    UserProfile getTestProfile() {
        Calendar cal = Calendar.getInstance();
        List<UserProfile.Address> addresses = Arrays.asList(
                TestUtils.createAddress(UserProfile.Address.Type.OFFICE, "46 Kippax", "Sydney", "NSW", "2000", "AU"));

        List<UserProfile.PhoneNumber> phoneNumbers = Arrays.asList(TestUtils.createPhoneNumber(UserProfile.PhoneNumber.Type.MOBILE, "098765432"));
        return TestUtils.createProfile("Bilal", "Fazal", cal.getTime(), phoneNumbers, addresses);
    }
}
