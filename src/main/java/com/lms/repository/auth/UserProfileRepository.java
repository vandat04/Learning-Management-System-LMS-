package com.lms.repository.auth;

import com.lms.entity.auth.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository  extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserId(Integer userId);
}
