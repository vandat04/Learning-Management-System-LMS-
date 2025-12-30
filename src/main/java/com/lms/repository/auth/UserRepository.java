package com.lms.repository.auth;

import com.lms.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  UserRepository extends JpaRepository <User, Long> {
    User findByEmail(String email);
    User findById(Integer userId);
    boolean existsByEmail(String email);

    @Query("""
          SELECT u.id
          FROM User u
          WHERE u.email like %:email%
    """)
    List<Integer> findUserIdsByEmailContaining(@Param("email") String email);

}
