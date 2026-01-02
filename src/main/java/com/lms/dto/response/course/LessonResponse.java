package com.lms.dto.response.course;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LessonResponse {
    private Integer id;
    private String title;
    private String videoUrl;
    private String documentUrl;
    private String contentSummary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
