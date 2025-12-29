package com.lms.dto.request.auth.profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserGetOTPRequest {
    private String email;
}
