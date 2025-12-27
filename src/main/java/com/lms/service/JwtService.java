package com.lms.service;

public interface JwtService {
    String generateToken(Integer userId, String email, String role);
    String extractEmail(String token);
    String extractRole(String token);
    Integer extractUserId(String token);
}
