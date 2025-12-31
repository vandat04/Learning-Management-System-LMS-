package com.lms.entity.learning;

import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "lesson_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lesson_id"})
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "lesson_id")
    private Integer lessonId;
    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}