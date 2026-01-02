package com.lms.dto.request.course;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InstructorCreateCourseRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal priceWithOnl;
    private List<Integer> listCategory;
    private Integer status;
}
