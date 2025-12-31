package com.lms.dto.request.auth.authentication;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
