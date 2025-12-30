package com.lms.service.impl.auth;

import com.cloudinary.Cloudinary;
import com.lms.service.core.auth.FileUploadService;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final Cloudinary cloudinary;
    private final Validate validate;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        validate.validateFile(file);
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "folder", "images"
                    )
            );
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            validate.saveError(2);
            return "";
        }
    }

    @Override
    public String uploadVideo(MultipartFile file) throws IOException {
        validate.validateFile(file);
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "video",
                            "folder", "videos"
                    )
            );
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            validate.saveError(2);
            return "";
        }
    }
}