package com.lms.service.core.course;

import com.lms.dto.request.course.InstructorCreateCourseRequest;
import com.lms.entity.course.Category;

import java.util.List;

public interface CourseService {
    List<Category> getAllCategories();
    void createNewCourse(InstructorCreateCourseRequest request);
//    void updateCourse()
}
