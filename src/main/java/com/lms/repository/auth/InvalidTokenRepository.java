package com.lms.repository.auth;

import com.lms.entity.auth.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Integer> {
    void deleteByExpiryTimeBefore(LocalDateTime now);
    boolean existsByToken(String token);
}
