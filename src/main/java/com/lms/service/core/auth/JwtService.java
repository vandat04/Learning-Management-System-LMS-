package com.lms.service.core.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

public interface JwtService {
    String generateToken(Integer userId, String email, String role);
    String extractEmail(String token);
    String extractRole(String token);
    Integer extractUserId(String token);
    Date getExpirationTime(String token);
    String extractToken(HttpServletRequest request);
}
