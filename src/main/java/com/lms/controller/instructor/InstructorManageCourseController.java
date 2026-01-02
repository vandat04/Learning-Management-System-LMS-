package com.lms.controller.instructor;

import com.lms.dto.request.course.InstructorCreateCourseRequest;
import com.lms.service.core.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor/course")
@RequiredArgsConstructor
public class InstructorManageCourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewCourse(@Valid @RequestBody InstructorCreateCourseRequest request){
        courseService.createNewCourse(request);
        return ResponseEntity.ok("Create successfully!!");
    }



}
