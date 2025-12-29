package com.lms.service.auth;

import com.lms.dto.request.auth.profile.SubmitInstructorApplicationRequest;
import com.lms.dto.request.auth.profile.UserChangePasswordRequest;
import com.lms.dto.request.auth.profile.UserGetOTPRequest;
import com.lms.dto.request.auth.profile.UserResetPasswordRequest;
import com.lms.dto.request.auth.profile.UserUpdateProfileRequest;
import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.entity.auth.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface UserService {
    User findByEmail(String email);
    String findRoleNamesByUserId(Integer userId);
    UserProfileResponse userUpdateProfile(UserUpdateProfileRequest request);
    UserProfileResponse userUpdateImageProfile(MultipartFile file) throws IOException;
    void changePassword(UserChangePasswordRequest request);
    void sendEmailRestPassword(UserGetOTPRequest request);
    void resetPassword(UserResetPasswordRequest request);
    UserProfileResponse getUserProfile();
    void submitInstructorApplication(SubmitInstructorApplicationRequest request) throws IOException;
}
