package com.bilal.service.profile.service;

import com.bilal.service.profile.exception.NotFoundException;
import com.bilal.service.profile.model.UserProfile;
import com.bilal.service.profile.model.UserProfile.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    // TODO: Repository should be used here to persist profiles. Temp implementation due to lack of time.
    private Map<Long, UserProfile> tempStorageMap = new HashMap<>();

    @Override
    public UserProfile getProfile(Long userId) {
        if (tempStorageMap.containsKey(userId)) {
            return tempStorageMap.get(userId);
        }
        throw new NotFoundException(String.format("Cannot update - Profile does not exist for userId: %s", userId));
    }

    @Override
    public UserProfile saveProfile(Long userId, UserProfile profile) {
        tempStorageMap.put(userId, profile);
        return profile;
    }

    @Override
    public UserProfile updateProfile(Long userId, UserProfile profile) {
        if (tempStorageMap.containsKey(userId)) {
            tempStorageMap.put(userId, profile);
            return profile;
        } else {
            throw new NotFoundException(String.format("Cannot update - Profile does not exist for userId: %s", userId));
        }
    }

    @Override
    public Address updateProfileAddress(Long userId, UserProfile.Address.Type addressType, UserProfile.Address address) {
        if (tempStorageMap.containsKey(userId)) {
            UserProfile profile = tempStorageMap.get(userId);
            profile.getAddressMap().put(addressType, address);
            return profile.getAddressMap().get(addressType);
        } else {
            throw new NotFoundException(String.format("Cannot update address, Profile does not exist for userId: %s", userId));
        }
    }

    @Override
    public boolean removeProfile(Long userId) {
        if (tempStorageMap.containsKey(userId)) {
            return tempStorageMap.remove(userId) != null;
        }
        throw new NotFoundException(String.format("Profile does not exist for userId: %s", userId));
    }
}
