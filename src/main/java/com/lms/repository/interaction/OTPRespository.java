package com.lms.repository.interaction;

import com.lms.entity.interaction.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRespository extends JpaRepository<OTP, Integer> {
    boolean existsByEmail(String email);
    OTP findByEmail(String email);
}
