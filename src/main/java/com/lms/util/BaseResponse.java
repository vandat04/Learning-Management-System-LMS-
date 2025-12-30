package com.lms.util;

import com.lms.dto.response.admin.user.InstructorApplicationResponse;
import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.entity.auth.*;
import com.lms.exception.AppException;
import com.lms.repository.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseResponse {

    private final Validate validate;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final InstructorApplicationRepository instructorApplicationRepository;
    private final CertificationRepository certificationRepository;

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

    public List<InstructorApplicationResponse> getInstructorApply(Integer userId){
        validate.checkNull(userId);

        List<InstructorApplicationResponse> list = new ArrayList<>();

        InstructorApplicationResponse profile;
        UserProfileResponse user = getUserProfile(userId);
        List<InstructorApplication> applyList = instructorApplicationRepository.findInstructorApplicationByUserId(userId);
        List<Certification> cerList;

        for (InstructorApplication item : applyList){
            cerList = certificationRepository.findCertificationByApplicationId(item.getId());

            profile = new InstructorApplicationResponse();
            profile.setUserProfileResponse(user);
            profile.setInstructorApplication(item);
            profile.setCertification(cerList);

            list.add(profile);
        }

        return list;
    }

    public InstructorApplicationResponse getSingleInstructorApply(Integer applicationId){
        //Get Instructor application by applicationId
        validate.checkNull(applicationId);
        InstructorApplication apply = instructorApplicationRepository.findById(applicationId).orElseThrow(()
                -> new AppException("Can not find apply by your id", HttpStatus.BAD_REQUEST));
        //Get user full infor
        Integer userId = apply.getUserId();
        UserProfileResponse user = getUserProfile(userId);
        //Get Certification by applicationId
        List<Certification> cerList = certificationRepository.findCertificationByApplicationId(applicationId);
        //Get Instructor application full
        InstructorApplicationResponse profile = new InstructorApplicationResponse();
        profile.setUserProfileResponse(user);
        profile.setInstructorApplication(apply);
        profile.setCertification(cerList);

        return profile;
    }
}
