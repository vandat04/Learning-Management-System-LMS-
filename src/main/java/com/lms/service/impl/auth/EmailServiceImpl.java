package com.lms.service.impl.auth;

import com.lms.entity.interaction.Email;
import com.lms.service.auth.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private  JavaMailSender mailSender;

    @Override
    public void sendEmailOTP(String code, String toMail) {

        Email email = new Email();
        email.setToEmail(toMail);
        email.setSubject("OTP to reset password");
        email.setBody("Your OTP is valid for 3 minutes:" + code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dattruong02112004@gmail.com");
        message.setTo(email.getToEmail());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        mailSender.send(message);
    }
}
