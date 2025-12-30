package com.lms.service.core.auth;

public interface EmailService {
    void sendEmailOTP(String code, String toEmail);
}
