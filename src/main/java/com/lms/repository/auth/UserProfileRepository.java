package com.lms.repository.auth;

import com.lms.entity.auth.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository  extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserId(Integer userId);
}
