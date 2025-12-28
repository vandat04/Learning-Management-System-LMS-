package com.lms.controller.auth;

import com.lms.dto.request.UserChangePasswordRequest;
import com.lms.dto.request.UserUpdateProfileRequest;
import com.lms.service.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile( @Valid @RequestBody UserUpdateProfileRequest request)
    {
        userService.userUpdateProfile(request);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword( @Valid @RequestBody UserChangePasswordRequest request)
    {
        userService.changePassword(request);
        return ResponseEntity.ok("Change password successfully");
    }

}
