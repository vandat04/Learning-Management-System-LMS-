package com.lms.entity.course;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "title")
    private String title;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "documents_url")
    private String documentsUrl;
    @Column(name = "content_summary")
    private String contentSummary;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
