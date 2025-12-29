package com.lms.dto.request.auth.profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
@Data
public class SubmitInstructorApplicationRequest {
    private List<String> title;
    private List<MultipartFile> file;
}


