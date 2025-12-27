package com.lms.service;

public interface EmailService {
    void sendEmailOTP(String code, String toEmail);
}
