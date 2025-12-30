package com.lms.controller.auth;

import com.lms.service.core.auth.AuthService;
import com.lms.service.core.auth.JwtService;
import com.lms.service.core.auth.LogoutService;
import com.lms.service.core.auth.UserService;
import com.lms.service.core.common.AIService;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class Test {

    private final AuthService authService;
    private final UserService userService;
    private final LogoutService logoutService;
    private final JwtService jwtService;
    private final Validate validate;
    private final AIService aiService;

    @GetMapping("/ai")
    public String check() {
        return aiService.checkText("oke");
    }


}