package com.bilal.service.profile.controller;

import com.bilal.service.profile.model.JsonResponse;
import com.bilal.service.profile.model.JsonResponse.JsonResponseStatus;
import com.bilal.service.profile.model.UserProfile;
import com.bilal.service.profile.model.UserProfile.Address;
import com.bilal.service.profile.model.UserProfile.PhoneNumber;
import com.bilal.service.profile.model.converter.AddressTypeConverter;
import com.bilal.service.profile.model.converter.PhoneTypeConverter;
import com.bilal.service.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Api(value = "User Profile Resource", description = "CRUD on User Profiles")
@RestController
@RequestMapping("/api/v1/profiles")
@Slf4j
public class UserProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * GET - get profile with userId
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "Get user's Profile")
    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public UserProfile get(@PathVariable("userId") Long userId) {
        return profileService.getProfile(userId);
    }

    /**
     * POST - Saves a new profile for userId
     *
     * @param userId
     * @param profile
     * @return
     */
    @ApiOperation(value = "Create user's profile")
    @PostMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JsonResponse post(@PathVariable("userId") Long userId, @RequestBody UserProfile profile) {
        UserProfile savedProfile = profileService.saveProfile(userId, profile);
        //Once DB integration is done, only sending the id as data should be enough.
        return JsonResponse.builder().status(JsonResponseStatus.SUCCESS).data(savedProfile).build();
    }

    /**
     * PUT - Updates an existing profile. Throws NotFoundException if no profile exists for given userId
     *
     * @param userId
     * @param profile
     * @return
     */
    @ApiOperation(value = "Update user's profile")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Profile not found")
    })
    @PutMapping("/{userId}")
    public JsonResponse put(@PathVariable("userId") Long userId, @RequestBody UserProfile profile) {
        profileService.updateProfile(userId, profile);
        return JsonResponse.builder().status(JsonResponseStatus.SUCCESS).data(userId).build();
    }

    /**
     * PATCH - Updates address in profile. Throws NotFoundException if no profile exists for given userId
     *
     * @param userId
     * @param addressType
     * @param address
     * @return
     */
    @ApiOperation(value = "Patch user's address on profile")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Profile not found")
    })
    @PatchMapping("/{userId}/address/{type}")
    public JsonResponse patchAddress(@PathVariable("userId") Long userId, @PathVariable("type") Address.Type addressType,
                                     @RequestBody Address address) {
        address = profileService.updateProfileAddress(userId, addressType, address);
        return JsonResponse.builder().status(JsonResponseStatus.SUCCESS).data(address).build();
    }

    /**
     * DELETE - Deletes a profile if exists, otherwise throw NotFoundException.
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "Delete user's profile")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Profile not found")})
    @DeleteMapping("/{userId}")
    public JsonResponse delete(@PathVariable("userId") Long userId) {
        boolean deleted = profileService.removeProfile(userId);
        return JsonResponse.builder()
                .status(deleted ? JsonResponseStatus.SUCCESS : JsonResponseStatus.ERROR)
                .data(deleted)
                .build();
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Address.Type.class, new AddressTypeConverter());
        dataBinder.registerCustomEditor(PhoneNumber.Type.class, new PhoneTypeConverter());
    }
}
