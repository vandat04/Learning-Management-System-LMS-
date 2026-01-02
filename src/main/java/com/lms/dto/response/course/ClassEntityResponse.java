package com.lms.dto.response.course;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClassEntityResponse {
    private Integer id;
    private String note;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

