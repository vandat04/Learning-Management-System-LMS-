package com.lms.repository.course;

import com.lms.entity.course.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassEntityRepository extends JpaRepository<ClassEntity, Integer> {
    Long countByCourseId(Integer courseId);
}
