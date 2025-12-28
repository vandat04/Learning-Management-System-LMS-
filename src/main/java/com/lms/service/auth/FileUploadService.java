package com.lms.service.auth;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileUploadService {
    String uploadImage(MultipartFile file) throws IOException;
    String uploadVideo(MultipartFile file) throws IOException;
}
