package com.lms.service.auth;

public interface EmailService {
    void sendEmailOTP(String code, String toEmail);
}
