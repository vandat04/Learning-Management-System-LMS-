package com.lms.repository.course;

import com.lms.entity.course.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    long countByCourseId(Integer courseId);
}
