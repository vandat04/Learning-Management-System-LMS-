package com.lms.service;

import com.lms.dto.request.UserChangePasswordRequest;
import com.lms.dto.request.UserGetOTPRequest;
import com.lms.dto.request.UserResetPasswordRequest;
import com.lms.dto.request.UserUpdateProfileRequest;
import com.lms.dto.response.UserProfileResponse;
import com.lms.entity.auth.User;
import com.lms.entity.auth.UserProfile;

public interface UserService {
    User findByEmail(String email);
    String findRoleNamesByUserId(Integer userId);
    UserProfileResponse userUpdateProfile(UserUpdateProfileRequest request);
    void changePassword(UserChangePasswordRequest request);
    void sendEmailRestPassword(UserGetOTPRequest request);
    void resetPassword(UserResetPasswordRequest request);
}
