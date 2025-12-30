package com.lms.dto.request.auth.profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AdminFeedbackInstructorApplicationRequest {
    private Integer isApproved; //1 = APPROVED , 2 = REJECTED
    private String adminFeedback;
}
