package com.lms.entity.assessment;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_results")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "quiz_id")
    private Integer quizId;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "score")
    private BigDecimal score;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
