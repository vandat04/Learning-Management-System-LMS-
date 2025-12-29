package com.lms.dto.request.auth.authentication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
}
