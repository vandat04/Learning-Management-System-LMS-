package com.lms.repository.auth;

import com.lms.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends JpaRepository <User, Long> {
    User findByEmail(String email);
    User findById(Integer userId);
    boolean existsByEmail(String email);

}
