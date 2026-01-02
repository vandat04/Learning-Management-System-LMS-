package com.lms.repository.learning;

import com.lms.entity.learning.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    long countByCourseId(Integer courseId);

    @Query("""
                SELECT COUNT(DISTINCT e.studentId)
                FROM Enrollment e
                WHERE e.courseId = :courseId
            """)
    long countStudentByCourseId(@Param("courseId") Integer courseId);

    @Query("""
                SELECT COUNT(DISTINCT e.classId)
                FROM Enrollment e
                WHERE e.courseId = :courseId
            """)
    long countClassByCourseId(@Param("courseId") Integer courseId);
}
