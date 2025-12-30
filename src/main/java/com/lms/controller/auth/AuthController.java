package com.lms.controller.auth;

import com.lms.dto.request.auth.authentication.LoginGoogleRequest;
import com.lms.dto.request.auth.authentication.LoginRequest;
import com.lms.dto.request.auth.authentication.RegisterRequest;
import com.lms.dto.request.auth.profile.UserGetOTPRequest;
import com.lms.dto.request.auth.profile.UserResetPasswordRequest;
import com.lms.service.core.auth.AuthService;
import com.lms.service.core.auth.JwtService;
import com.lms.service.core.auth.LogoutService;
import com.lms.service.core.auth.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final LogoutService logoutService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "access_token", token,
                    "token_type", "Bearer"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle( @RequestBody LoginGoogleRequest request) {

        try {
            String token = authService.loginWithGoogle(request);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "access_token", token,
                    "token_type", "Bearer"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
            authService.registerStudent(request);
            return ResponseEntity.ok("Register success");
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMailResetPassword(@RequestBody UserGetOTPRequest request) {
        userService.sendEmailRestPassword(request);
        return ResponseEntity.ok("Send email success");
    }

    @PutMapping("/password")
    public ResponseEntity<?> resetPassword(@RequestBody UserResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("Reset password success. Please login again!");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        logoutService.logout(jwtService.extractToken(request));
        return ResponseEntity.ok("Logged out");
    }

}