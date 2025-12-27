package com.lms.util;

import com.lms.entity.auth.User;
import com.lms.entity.interaction.OTP;
import com.lms.exception.AppException;
import com.lms.repository.auth.UserRepository;
import com.lms.repository.interaction.OTPRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class Validate {

    private final UserRepository userRepository;
    private final OTPRespository otpRespository;
    private final PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    //0.Check null
    public void checkNull(String request) {
        if (request == null) {
            throw new AppException("The element cannot be left blank!", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkNull(Integer request) {
        if (request == null) {
            throw new AppException("The element cannot be left blank!", HttpStatus.BAD_REQUEST);
        }
    }

    //1. Check mail
    public void validateEmail(String email) {
        checkEmailNotExists(email);
        validateGmailFormat(email);
    }

    public void checkEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AppException("This email already exists in the system!", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkEmailExists(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new AppException("This email not exists in the system!", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateGmailFormat(String email) {
        email = email.trim().toLowerCase();

        if (!email.endsWith("@gmail.com")) {
            throw new AppException("This email wrong format!", HttpStatus.BAD_REQUEST);
        }
    }

    //2. Check pass
    public void validatePassword(String password) {
        if (password.length() < 8) {
            throw new AppException("The password must have at least 8 characters!", HttpStatus.BAD_REQUEST);
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")) {
            throw new AppException(
                    "Passwords must include uppercase letters, lowercase letters, numbers, and special characters!",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public void checkPasswordMatch(String password, String passwordHash){
        if (!passwordEncoder.matches(password, passwordHash)) {
            throw new AppException("Wrong password", HttpStatus.BAD_REQUEST);
        }
    }

    //3. Check full name
    public void validateFullName(String fullName) {
        if (fullName.trim().isEmpty()) {
            throw new AppException("The full name cannot be left blank!", HttpStatus.BAD_REQUEST);
        }

        if (fullName.length() < 2 || fullName.length() > 50) {
            throw new AppException("Full name must be between 2 and 50 characters long!", HttpStatus.BAD_REQUEST);
        }

        if (!fullName.matches( "^[\\p{L} .'-]+$")) {
            throw new AppException("Full names must not contain numbers or special characters!", HttpStatus.BAD_REQUEST);
        }
    }

    //4. Check phone
    public void validatePhone(String phone) {
        if (phone.trim().isEmpty()) {
            throw new AppException("The phone number cannot be left blank!", HttpStatus.BAD_REQUEST);
        }

        if (!phone.matches("^0[0-9]{9}$")) {
            throw new AppException("Phone numbers must have 10 digits and start with the number 0!", HttpStatus.BAD_REQUEST);
        }
    }

    //5. Check user là LOCAL hay GOOGLE
    public void checkLocalAccountForGoogleLogin(User user){
        if (user != null && user.getAuthProvider().equals("LOCAL")) {
            throw new AppException(
                    "This email is already registered with password. Please login using email & password.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public void checkGoogleAccountForGoogleLogin(User user){
        if (user != null && user.getAuthProvider().equals("GOOGLE")) {
            throw new AppException(
                    "This email is already registered with password. Please login using email & password.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    //6. Check user active or not
    public void checkActive(Boolean status){
        if (!status) {
            throw new AppException( "The account is not active", HttpStatus.BAD_REQUEST);
        }
    }

    //7. Get OTP
    public String getOTP(){
        String otp = "";
        for (int i=0 ; i<6 ; i++){
            int num = new Random().nextInt(CHARACTERS.length());
            otp += CHARACTERS.charAt(num);
        }
        return otp;
    }

    public void checkOTP(String code, String email){
        OTP otp = otpRespository.findByEmail(email);
        if (!checkOTPMatch(code,otp.getOtp() )){
            throw new AppException( "Wrong OTP code. Please try again!", HttpStatus.BAD_REQUEST);
        }
        if (otp.getEnd_at().isBefore(LocalDateTime.now())){
            throw new AppException( "The OTP has expired, please get another code!", HttpStatus.BAD_REQUEST);
        }

    }

    public boolean checkOTPMatch(String code, String codeHash){
        return passwordEncoder.matches(code, codeHash);
    }

}
