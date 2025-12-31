package com.lms.dto.request.auth.profile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class SubmitInstructorApplicationRequest {
    private List<String> title;
    private List<MultipartFile> file;
}


