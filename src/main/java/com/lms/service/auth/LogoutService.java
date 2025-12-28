package com.lms.service.auth;

import java.time.LocalDateTime;

public interface LogoutService {
    void logout(String token);
    void deleteByExpiryTimeBefore(LocalDateTime now);
    boolean existsByToken(String token);
}
