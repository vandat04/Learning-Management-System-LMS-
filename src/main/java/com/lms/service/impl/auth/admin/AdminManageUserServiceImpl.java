package com.lms.service.impl.auth.admin;

import com.lms.dto.request.auth.profile.*;
import com.lms.dto.response.admin.user.InstructorApplicationResponse;
import com.lms.entity.auth.InstructorApplication;
import com.lms.entity.auth.UserRole;
import com.lms.exception.AppException;
import com.lms.repository.auth.*;
import com.lms.repository.interaction.OTPRespository;
import com.lms.service.core.auth.admin.AdminManageUserService;
import com.lms.service.core.auth.EmailService;
import com.lms.service.core.auth.FileUploadService;
import com.lms.util.BaseResponse;
import com.lms.util.Util;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminManageUserServiceImpl implements AdminManageUserService {

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
    private final Util util;

    public List<Integer> getUserIdInstructorApplication() {
        return instructorApplicationRepository.getUserIdListInstructorApplication();
    }


    @Override
    public List<InstructorApplicationResponse> getInstructorApplicationByAdmin(Integer userId) {
        return baseResponse.getInstructorApply(userId);
    }

    @Override
    public List<InstructorApplicationResponse> getInstructorApplicationByAdmin() {
        List<Integer> listUser = getUserIdInstructorApplication();
        List<InstructorApplicationResponse> listApplication = new ArrayList<>();
        for (Integer item : listUser){
            listApplication.addAll(getInstructorApplicationByAdmin(item));
        }
        Collections.reverse(listApplication);
        return listApplication;
    }

    @Override
    public InstructorApplicationResponse getSingleInstructorApplicationByAdmin(Integer applicationId) {
        return baseResponse.getSingleInstructorApply(applicationId);
    }

    @Override
    public void updateStatusInstructorApplicationByAdmin(Integer applicationId, AdminFeedbackInstructorApplicationRequest request) {
        Integer isApproved = request.getIsApproved();
        String adminFeedback = request.getAdminFeedback();
        validate.checkNull(isApproved);
        validate.validateBio(adminFeedback);
        validate.validateByAI(adminFeedback);

        if (isApproved != 1 && isApproved != 2){
            throw new AppException("Update with invalid status",HttpStatus.BAD_REQUEST);
        }

        InstructorApplication instructorApplication = instructorApplicationRepository.findById(applicationId).orElseThrow(()
                -> new AppException("Can not find apply by your id", HttpStatus.BAD_REQUEST));

        instructorApplication.setIsApproved(isApproved);
        instructorApplication.setAdminFeedback(adminFeedback);
        instructorApplication.setReviewedAt(LocalDateTime.now());

        if (isApproved == 1){
            Integer userId = instructorApplication.getUserId();
            UserRole userRole = userRoleRepository.findByUserId(userId);
            userRole.setRoleId(2); // INSTRUCTOR
            userRoleRepository.save(userRole);
        }

        instructorApplicationRepository.save(instructorApplication);
    }

    @Override
    public List<InstructorApplicationResponse> findInstructorApplicationByAdmin(String email) {
        List<Integer> listUser = util.getUserIdByEmail(email);
        List<InstructorApplicationResponse> listApplication = new ArrayList<>();
        for(Integer item : listUser){
            listApplication.addAll(getInstructorApplicationByAdmin(item));
        }
        Collections.reverse(listApplication);
        return listApplication;
    }

    @Override
    public List<InstructorApplicationResponse> findInstructorApplicationByApproveFilter(Integer isApprove) {
        validate.checkNull(isApprove);
        List<InstructorApplication> listApply = instructorApplicationRepository.findByIsApproved(isApprove);
        List<InstructorApplicationResponse> listApplication = new ArrayList<>();
        for(InstructorApplication item : listApply){
            listApplication.add(baseResponse.getSingleInstructorApply(item.getId()));
        }
        Collections.reverse(listApplication);
        return listApplication;
    }

    @Override
    public List<InstructorApplicationResponse> findInstructorApplicationByBetweenDayFilter(LocalDateTime start, LocalDateTime end) {
        validate.checkEndBeforeStart(start, end);
        List<InstructorApplication> listApply = instructorApplicationRepository.findBySubmittedAtBetween(start, end);
        List<InstructorApplicationResponse> listApplication = new ArrayList<>();
        for(InstructorApplication item : listApply){
            listApplication.add(baseResponse.getSingleInstructorApply(item.getId()));
        }
        Collections.reverse(listApplication);
        return listApplication;
    }

}
