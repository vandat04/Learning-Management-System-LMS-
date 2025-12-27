package com.lms.repository.auth;

import com.lms.entity.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query("""
        SELECT r.name
        FROM Role r, UserRole ur
        WHERE r.id = ur.roleId
          AND ur.userId = :userId
    """)
    String findRoleNamesByUserId(@Param("userId") Integer userId);

    UserRole findByUserId(Integer userId);
}
