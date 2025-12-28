package com.lms.repository.auth;

import com.lms.entity.auth.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Integer> {
    void deleteByExpiryTimeBefore(LocalDateTime now);
    boolean existsByToken(String token);
}
