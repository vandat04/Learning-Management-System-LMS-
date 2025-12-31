package com.lms.dto.request.auth.profile;

import lombok.Data;

@Data
public class UserChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
