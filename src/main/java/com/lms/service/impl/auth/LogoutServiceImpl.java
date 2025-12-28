package com.lms.service.impl.auth;

import com.lms.entity.auth.InvalidToken;
import com.lms.repository.auth.InvalidTokenRepository;
import com.lms.service.auth.JwtService;
import com.lms.service.auth.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final InvalidTokenRepository invalidTokenRepository;
    private final JwtService jwtService;

    @Override
    public void logout(String token) {
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setToken(token);
        invalidToken.setExpiryTime(jwtService.getExpirationTime(token));
        invalidTokenRepository.save(invalidToken);
    }

    @Override
    public void deleteByExpiryTimeBefore(LocalDateTime now) {
        invalidTokenRepository.deleteByExpiryTimeBefore(now);
    }

    @Override
    public boolean existsByToken(String token) {
        return invalidTokenRepository.existsByToken(token);
    }
}
