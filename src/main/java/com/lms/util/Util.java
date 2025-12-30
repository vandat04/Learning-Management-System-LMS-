package com.lms.util;

import com.lms.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Util {

    private final Validate validate;
    private final UserRepository userRepository;

    public List<Integer> getUserIdByEmail(String email) {
        validate.validateEmailFormat(email);
        email = email.substring(0, email.lastIndexOf("@"));
        return userRepository.findUserIdsByEmailContaining(email);
    }
}
