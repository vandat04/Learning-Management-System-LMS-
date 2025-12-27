package com.lms.service.impl;

import com.lms.dto.request.UserChangePasswordRequest;
import com.lms.dto.request.UserGetOTPRequest;
import com.lms.dto.request.UserResetPasswordRequest;
import com.lms.dto.request.UserUpdateProfileRequest;
import com.lms.dto.response.UserProfileResponse;
import com.lms.entity.auth.User;
import com.lms.entity.auth.UserProfile;
import com.lms.entity.interaction.OTP;
import com.lms.exception.AppException;
import com.lms.repository.auth.RoleRepository;
import com.lms.repository.auth.UserProfileRepository;
import com.lms.repository.auth.UserRepository;
import com.lms.repository.auth.UserRoleRepository;
import com.lms.repository.interaction.OTPRespository;
import com.lms.service.EmailService;
import com.lms.service.UserService;
import com.lms.util.BaseResponse;
import com.lms.util.SecurityUtil;
import com.lms.util.Validate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserProfileRepository userProfileRepository;
    private final OTPRespository otpRespository;
    private final EmailService emailService;
    private final Validate validate;
    private final BaseResponse baseResponse;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AppException("Email not found: " + email, HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    @Override
    public String findRoleNamesByUserId(Integer userId) {
        String role = userRoleRepository.findRoleNamesByUserId(userId);
        if (role == null || role.isEmpty()) {
            log.warn("No role found for user id: {}", userId);
            return "USER"; // Default role
        }
        return role;
    }

    @Override
    @Transactional
    public UserProfileResponse userUpdateProfile(UserUpdateProfileRequest request) {

        Integer userId = SecurityUtil.getCurrentUserId();
        String fullName = request.getFullName();
        String phone = request.getPhone();
        String avatarUrl = request.getAvatarUrl();
        String bio = request.getBio();

        validate.checkNull(userId);

        User user = userRepository.findById(userId);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        userProfile.setUserId(userId);
        if (fullName != null ) {
            validate.validateFullName(fullName);
            userProfile.setFullName(fullName);
        }
        if (phone != null) {
            validate.validatePhone(phone);
            userProfile.setPhone(phone);
        }
        if (avatarUrl != null) userProfile.setAvatarUrl(avatarUrl);
        if (bio != null) userProfile.setBio(bio);
        userProfileRepository.save(userProfile);

        return baseResponse.getUserProfile(userId);
    }

    @Override
    public void changePassword(UserChangePasswordRequest request) {

        Integer userId = SecurityUtil.getCurrentUserId();
        validate.checkNull(userId);
        User user = userRepository.findById(userId);

        String oldPassword = request.getOldPassword();

        validate.checkPasswordMatch(oldPassword,user.getPasswordHash() );

        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();
        validate.checkNull(newPassword);
        validate.validatePassword(newPassword);
        if (!newPassword.equals(confirmPassword)) {
            throw new AppException("The new password and the confirm password are different.", HttpStatus.BAD_REQUEST);
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public void sendEmailRestPassword(UserGetOTPRequest request) {
        //Kiểm tra xem email đã tồn tại tài khoảng hay chưa/ loại tài khoản là gi/ có còn hoạt động không
        String email = request.getEmail();
        validate.checkNull(email);
        validate.checkEmailExists(email);

        User user = userRepository.findByEmail(email);
        validate.checkGoogleAccountForGoogleLogin(user);
        validate.checkActive(user.getIsActive());

        //Kiểm tra xem đã có yêu cầu tạo OTP trc đó hay chưa
        OTP otp = otpRespository.findByEmail(email);
        if ( otp == null ){
            otp = new OTP();
            otp.setEmail(email);
        }
        String code = validate.getOTP();
        otp.setOtp(passwordEncoder.encode(code));
        LocalDateTime now = LocalDateTime.now();
        otp.setStart_at(now);
        otp.setEnd_at(now.plusMinutes(3));

        otpRespository.save(otp);
        emailService.sendEmailOTP(code, email);
    }

    @Override
    public void resetPassword(UserResetPasswordRequest request) {
        String email = request.getUserGetOTPRequest().getEmail();
        validate.checkNull(email);
        String code = request.getCode();
        validate.checkNull(code);

        validate.checkOTP(code, email);

        String newPassword = request.getUserChangePasswordRequest().getNewPassword();
        String confirmPassword = request.getUserChangePasswordRequest().getConfirmPassword();
        validate.checkNull(newPassword);
        validate.validatePassword(newPassword);
        if (!newPassword.equals(confirmPassword)) {
            throw new AppException("The new password and the confirm password are different.", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(email);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
