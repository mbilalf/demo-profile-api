package com.bilal.service.profile.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class JsonResponse {
    public static enum JsonResponseStatus {
        SUCCESS("success"),
        ERROR("error"),
        UNAUTHORISED("unauthorized"),
        NOT_FOUND("not_found");

        private String value;
        JsonResponseStatus(String value){
            this.value = value;
        }

    }

    JsonResponseStatus status;
    String message;
    Object data;

}
