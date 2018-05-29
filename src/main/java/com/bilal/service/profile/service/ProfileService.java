package com.bilal.service.profile.service;

import com.bilal.service.profile.model.UserProfile;
import com.bilal.service.profile.model.UserProfile.Address;

public interface ProfileService {

    UserProfile getProfile(Long userId);

    UserProfile saveProfile(Long userId, UserProfile profile);

    UserProfile updateProfile(Long userId, UserProfile profile);

    Address updateProfileAddress(Long userId, UserProfile.Address.Type addressType, UserProfile.Address address);

    boolean removeProfile(Long userId);
}
