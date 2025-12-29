package com.lms.controller.auth;

import com.lms.dto.request.auth.profile.SubmitInstructorApplicationRequest;
import com.lms.dto.request.auth.profile.UserChangePasswordRequest;
import com.lms.dto.request.auth.profile.UserUpdateProfileRequest;
import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.service.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/user/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserUpdateProfileRequest request) {
        UserProfileResponse response = userService.userUpdateProfile(request);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Profile updated successfully",
                        "data", response
                )
        );
    }

    @PutMapping(value = "/profile/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileImage( @RequestParam("file") MultipartFile file) throws IOException {
        UserProfileResponse response = userService.userUpdateImageProfile(file);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Profile image updated successfully",
                        "data", response
                )
        );
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword( @Valid @RequestBody UserChangePasswordRequest request)
    {
        userService.changePassword(request);
        return ResponseEntity.ok("Change password successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        UserProfileResponse response = userService.getUserProfile();
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get profile successfully",
                        "data", response
                )
        );
    }

    @PostMapping("/apply-instructor")
    public ResponseEntity<?> applyInstructor(@ModelAttribute SubmitInstructorApplicationRequest request) throws IOException {
        userService.submitInstructorApplication(request);
        return ResponseEntity.ok("Get profile successfully");
    }

}
