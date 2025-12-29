package com.lms.repository.auth;

import com.lms.entity.auth.InstructorApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface InstructorApplicationRepository extends JpaRepository<InstructorApplication, Integer> {

    @Query("""
        SELECT COUNT(ia)
        FROM InstructorApplication ia
        WHERE ia.userId = :userId
          AND ia.submittedAt >= :startOfDay
          AND ia.submittedAt < :endOfDay
    """)
    Long countApplyByUserId(
            @Param("userId") Integer userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
