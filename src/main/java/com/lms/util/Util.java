package com.lms.util;

import com.lms.exception.AppException;
import com.lms.repository.auth.UserRepository;
import com.lms.repository.auth.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Util {

    private final Validate validate;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public List<Integer> getUserIdByEmail(String email) {
        validate.validateEmailFormat(email);
        email = email.substring(0, email.lastIndexOf("@"));
        return userRepository.findUserIdsByEmailContaining(email);
    }

    public Integer getUserId() {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new AppException("Please login!!", HttpStatus.BAD_REQUEST);
        }
        return userId;
    }

    public boolean checkAdminRole() {
        Integer userId = getUserId();
        return  userRoleRepository.findByUserId(userId).getRoleId() == 1;
    }

    public boolean checkInstructorRole() {
        Integer userId = getUserId();
        return  userRoleRepository.findByUserId(userId).getRoleId() == 2;
    }

    public boolean checkUserRole() {
        Integer userId = getUserId();
        return  userRoleRepository.findByUserId(userId).getRoleId() == 3;
    }

}
