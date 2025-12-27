package com.lms.service;

import com.lms.dto.request.LoginGoogleRequest;
import com.lms.dto.request.LoginRequest;
import com.lms.dto.request.RegisterRequest;

public interface AuthService {
    String login(LoginRequest request);
    void registerStudent(RegisterRequest request);
    String loginWithGoogle(LoginGoogleRequest request);
}
