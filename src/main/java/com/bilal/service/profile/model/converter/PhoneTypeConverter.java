package com.bilal.service.profile.model.converter;

import com.bilal.service.profile.exception.ProfileServiceException;
import com.bilal.service.profile.model.UserProfile.PhoneNumber;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyEditorSupport;

@Slf4j
public class PhoneTypeConverter extends PropertyEditorSupport {

    public void setAsText(final String text) {
        try {
            setValue(PhoneNumber.Type.valueOf(text));
        } catch (IllegalArgumentException iae) {
            String msg = String.format("Invalid Phone type value '%s'", text);
            log.error(msg);
            throw new ProfileServiceException(msg);
        }
    }
}
