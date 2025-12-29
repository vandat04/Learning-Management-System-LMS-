package com.lms.controller.auth;

import com.lms.dto.request.auth.authentication.LoginGoogleRequest;
import com.lms.dto.request.auth.authentication.LoginRequest;
import com.lms.dto.request.auth.authentication.RegisterRequest;
import com.lms.dto.request.auth.profile.UserGetOTPRequest;
import com.lms.dto.request.auth.profile.UserResetPasswordRequest;
import com.lms.service.auth.AuthService;
import com.lms.service.auth.JwtService;
import com.lms.service.auth.LogoutService;
import com.lms.service.auth.UserService;
import com.lms.service.common.AIService;
import com.lms.util.Validate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class Test {

    private final AuthService authService;
    private final UserService userService;
    private final LogoutService logoutService;
    private final JwtService jwtService;
    private final Validate validate;
    private final AIService aiService;

    @GetMapping("/ai")
    public String check() {
        return aiService.checkText("oke");
    }


}