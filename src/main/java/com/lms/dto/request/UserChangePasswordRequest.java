package com.lms.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
