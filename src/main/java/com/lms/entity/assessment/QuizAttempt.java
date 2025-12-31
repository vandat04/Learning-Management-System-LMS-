package com.lms.entity.assessment;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "quiz_id")
    private Integer quizId;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    @Column(name = "status")
    private Integer status;
}
