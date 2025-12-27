package com.lms.entity.learning;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "class_id")
    private Integer classId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
