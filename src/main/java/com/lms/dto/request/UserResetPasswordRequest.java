package com.lms.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserResetPasswordRequest {
    private UserGetOTPRequest userGetOTPRequest;
    private String code;
    private UserChangePasswordRequest userChangePasswordRequest;
}
