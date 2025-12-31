package com.lms.dto.request.auth.profile;

import lombok.Data;

@Data
public class AdminFeedbackInstructorApplicationRequest {
    private Integer isApproved; //1 = APPROVED , 2 = REJECTED
    private String adminFeedback;
}
