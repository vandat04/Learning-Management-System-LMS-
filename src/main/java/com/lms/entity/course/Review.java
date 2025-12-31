package com.lms.entity.course;

import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "instructor_id")
    private Integer instructorId;
    @Column(name = "rating_course")
    private BigDecimal ratingCourse;
    @Column(name = "rating_instructor")
    private BigDecimal ratingInstructor;
    @Column(name = "comment_course")
    private String commentCourse;
    @Column(name = "comment_instructor")
    private String commentInstructor;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}