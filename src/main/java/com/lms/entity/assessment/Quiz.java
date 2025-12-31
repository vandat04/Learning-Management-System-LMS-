package com.lms.entity.assessment;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "lesson_id")
    private Integer lessonId;
    @Column(name = "class_id")
    private Integer classId;
    @Column(name = "title")
    private String title;
    @Column(name = "deadline")
    private LocalDateTime deadline;
    @Column(name = "max_questions")
    private Integer maxQuestions;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}