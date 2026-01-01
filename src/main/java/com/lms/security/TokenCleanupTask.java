package com.lms.security;

import com.lms.service.core.auth.LogoutService;
import com.lms.service.core.payment.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class TokenCleanupTask {

    private final LogoutService logoutService;
    private final PaymentService paymentService;

    // Chạy mỗi giờ một lần (3600000 ms)
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cleanupExpiredTokens() {
        logoutService.deleteByExpiryTimeBefore(LocalDateTime.now());
        System.out.println("Expired tokens have been cleaned from the database.");
    }

    @Scheduled(fixedRate = 60000) // mỗi 1 phút
    public void checkExpiredPayments() {
        paymentService.handleQRExpiredPayments();
    }
}