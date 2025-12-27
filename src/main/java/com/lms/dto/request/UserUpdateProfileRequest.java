package com.lms.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserUpdateProfileRequest {
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String bio;
}
