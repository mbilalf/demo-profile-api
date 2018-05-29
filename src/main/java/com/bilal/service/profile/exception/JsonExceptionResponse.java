package com.bilal.service.profile.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class JsonExceptionResponse {
    int code;
    String message;
}
