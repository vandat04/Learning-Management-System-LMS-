package com.lms.util;

import com.lms.dto.response.UserProfileResponse;
import com.lms.entity.auth.Role;
import com.lms.entity.auth.User;
import com.lms.entity.auth.UserProfile;
import com.lms.entity.auth.UserRole;
import com.lms.exception.AppException;
import com.lms.repository.auth.RoleRepository;
import com.lms.repository.auth.UserProfileRepository;
import com.lms.repository.auth.UserRepository;
import com.lms.repository.auth.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseResponse {

    private final Validate validate;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public UserProfileResponse getUserProfile(Integer userId){
        validate.checkNull(userId);

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        User user = userRepository.findById(userId);
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(userRole.getRoleId())
                .orElseThrow(() -> new AppException("Role error", HttpStatus.BAD_REQUEST));

        userProfileResponse.setUserId(userId);
        userProfileResponse.setGmail(user.getEmail());
        userProfileResponse.setFullName(userProfile.getFullName());
        userProfileResponse.setPhone(userProfile.getPhone());
        userProfileResponse.setAvatarUrl(userProfile.getAvatarUrl());
        userProfileResponse.setBio(userProfile.getBio());
        userProfileResponse.setRatingAvg(userProfile.getRatingAvg());
        userProfileResponse.setNumberReview(userProfile.getNumberReview());
        userProfileResponse.setRole(role.getName());
        userProfileResponse.setActive(user.getIsActive());
        userProfileResponse.setCreatedAt(user.getCreatedAt());
        userProfileResponse.setUpdatedAt(user.getUpdatedAt());

        return userProfileResponse;
    }
}
