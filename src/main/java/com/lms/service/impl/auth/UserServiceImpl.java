package com.lms.service.impl.auth;

import com.lms.dto.request.auth.profile.*;
import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.entity.auth.Certification;
import com.lms.entity.auth.InstructorApplication;
import com.lms.entity.auth.User;
import com.lms.entity.auth.UserProfile;
import com.lms.entity.interaction.OTP;
import com.lms.exception.AppException;
import com.lms.repository.auth.*;
import com.lms.repository.interaction.OTPRespository;
import com.lms.service.core.auth.EmailService;
import com.lms.service.core.auth.FileUploadService;
import com.lms.service.core.auth.UserService;
import com.lms.util.BaseResponse;
import com.lms.util.SecurityUtil;
import com.lms.util.Validate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserProfileRepository userProfileRepository;
    private final OTPRespository otpRespository;
    private final EmailService emailService;
    private final FileUploadService fileUploadService;
    private final Validate validate;
    private final BaseResponse baseResponse;
    private final PasswordEncoder passwordEncoder;
    private final InstructorApplicationRepository instructorApplicationRepository;
    private final CertificationRepository certificationRepository;

    public Integer getUserId() {
        Integer userId = SecurityUtil.getCurrentUserId();
        validate.checkNull(userId);
        return userId;
    }

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

        Integer userId = getUserId();

        String fullName = request.getFullName();
        String phone = request.getPhone();
        String bio = request.getBio();

        validate.checkNull(userId);

        User user = userRepository.findById(userId);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (fullName != null) {
            validate.validateFullName(fullName);
            validate.validateByAI(fullName);
            userProfile.setFullName(fullName);
        }
        if (phone != null) {
            validate.validatePhone(phone);
            userProfile.setPhone(phone);
        }
        if (bio != null) {
            validate.validateBio(bio);
            validate.validateByAI(bio);
            userProfile.setBio(bio);
        }

        userProfileRepository.save(userProfile);

        return baseResponse.getUserProfile(userId);
    }

    @Override
    public UserProfileResponse userUpdateImageProfile(MultipartFile file) throws IOException {

        Integer userId = getUserId();
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        String avetarUrl = fileUploadService.uploadImage(file);
        userProfile.setAvatarUrl(avetarUrl);
        userProfileRepository.save(userProfile);
        return baseResponse.getUserProfile(userId);
    }

    @Override
    public void changePassword(UserChangePasswordRequest request) {
        Integer userId = getUserId();
        validate.checkNull(userId);
        User user = userRepository.findById(userId);

        String oldPassword = request.getOldPassword();

        validate.checkPasswordMatch(oldPassword, user.getPasswordHash());

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
        if (otp == null) {
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

    @Override
    public UserProfileResponse getUserProfile() {
        Integer userId = getUserId();
        return baseResponse.getUserProfile(userId);
    }

    @Override
    public void submitInstructorApplication(SubmitInstructorApplicationRequest request) throws IOException {
        //Láy userId từ token, hạn chế spam
        Integer userId = getUserId();
        validate.checkSpamApplyInstructor(userId);

        List<String> titleList = request.getTitle();
        List<MultipartFile> fileList = request.getFile();
        validate.checkNull(titleList);
        validate.checkNull(fileList);
        if (titleList.size() != fileList.size()) {
            throw new AppException("Insufficient titles for the files.", HttpStatus.BAD_REQUEST);
        }

        //Check and Add certification into application
        List<Certification> certificationList = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            validate.checkNull(title);
            validate.validateByAI(title);
            validate.validateBio(title);

            MultipartFile file = fileList.get(i);
            validate.validateFile(file);

            Certification certification = new Certification();
            certification.setTitle(title);
            certification.setDocumentUrl(fileUploadService.uploadImage(file));
            //Add
            certificationList.add(certification);
        }

        //Create new application
        InstructorApplication instructorApplication = new InstructorApplication();
        instructorApplication.setUserId(userId);
        instructorApplication.setIsApproved(0);
        instructorApplication.setSubmittedAt(LocalDateTime.now());
        Integer applicationId = instructorApplicationRepository.save(instructorApplication).getId();

        //Save
        for(Certification item : certificationList){
            item.setApplicationId(applicationId);
            certificationRepository.save(item);
        }
    }

}
