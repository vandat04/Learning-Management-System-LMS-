package com.lms.dto.response.course;

import com.lms.dto.response.auth.profile.UserProfileResponse;
import com.lms.entity.course.Category;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourseDetailsResponse {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal priceWithOnl;
    private String status;
    private BigDecimal ratingAvg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer approveBy;
    private LocalDateTime approveAt;
    private UserProfileResponse instructorProfileResponse;
    private List<Category> listCourseCategory;
    private Long countLesson;
    private Long countClass;
    private Long countStudent;
}

