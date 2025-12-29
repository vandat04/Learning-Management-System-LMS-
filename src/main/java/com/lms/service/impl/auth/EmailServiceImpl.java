package com.lms.service.impl.auth;

import com.lms.entity.interaction.Email;
import com.lms.exception.AppException;
import com.lms.service.auth.EmailService;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private  JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String fromMail;

    private final Validate validate;

    @Override
    public void sendEmailOTP(String code, String toMail) {
        try {
            Email email = new Email();
            email.setToEmail(toMail);
            email.setSubject("OTP to reset password");
            email.setBody("Your OTP is valid for 3 minutes: " + code);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromMail);
            message.setTo(email.getToEmail());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            mailSender.send(message);
        } catch (Exception e) {
            validate.saveError(3);
            throw new AppException("The email service is experiencing errors, please try again later.",  HttpStatus.BAD_REQUEST);
        }
    }
}
