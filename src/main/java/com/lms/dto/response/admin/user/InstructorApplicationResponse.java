package com.lms.dto.response.admin.user;

import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.entity.auth.Certification;
import com.lms.entity.auth.InstructorApplication;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Data
public class InstructorApplicationResponse {
    private UserProfileResponse userProfileResponse;
    private InstructorApplication instructorApplication;
    private List<Certification> certification;
}
