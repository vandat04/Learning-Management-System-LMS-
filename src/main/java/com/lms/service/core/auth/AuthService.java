package com.lms.service.core.auth;

import com.lms.dto.request.auth.authentication.LoginGoogleRequest;
import com.lms.dto.request.auth.authentication.LoginRequest;
import com.lms.dto.request.auth.authentication.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);
    void registerStudent(RegisterRequest request);
    String loginWithGoogle(LoginGoogleRequest request);
}
