package com.lms.service.core.auth.admin;

import com.lms.dto.request.auth.profile.AdminFeedbackInstructorApplicationRequest;
import com.lms.dto.response.admin.user.InstructorApplicationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminManageUserService {
    //Admin: Update Become Instructor
    List<InstructorApplicationResponse> getInstructorApplicationByAdmin(Integer userId);
    List<InstructorApplicationResponse> getInstructorApplicationByAdmin();
    InstructorApplicationResponse getSingleInstructorApplicationByAdmin(Integer applicationId);
    void updateStatusInstructorApplicationByAdmin(Integer applicationId, AdminFeedbackInstructorApplicationRequest request);
    List<InstructorApplicationResponse> findInstructorApplicationByAdmin(String email);
    List<InstructorApplicationResponse> findInstructorApplicationByApproveFilter(Integer isApprove);
    List<InstructorApplicationResponse> findInstructorApplicationByBetweenDayFilter(LocalDateTime start, LocalDateTime end);
}
