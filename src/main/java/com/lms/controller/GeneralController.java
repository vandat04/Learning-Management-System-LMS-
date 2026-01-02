package com.lms.controller;

import com.lms.dto.response.course.CourseDetailsResponse;
import com.lms.entity.course.Category;
import com.lms.service.core.course.CourseService;
import com.lms.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GeneralController {

    private final CourseService courseService;
    private final BaseResponse baseResponse;

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return courseService.getAllCategories();
    }

    @GetMapping("/count")
    public CourseDetailsResponse getA(){
        return baseResponse.getFullInformationOfCourse(4);
    }
}
