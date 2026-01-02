package com.lms.repository.course;

import com.lms.entity.course.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Integer> {
    List<CourseCategory> findByCourseId(Integer courseId);
    boolean existsByCourseIdAndCategoryId(Integer courseId, Integer categoryId);

    @Query("""
          SELECT cc.categoryId
          FROM CourseCategory cc
          WHERE cc.courseId = :courseId
    """)
    List<Integer> findCategoryIdByCourseId(@Param("courseId") Integer courseId);
}
