package com.lms.entity.course;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "instructor_id")
    private Integer instructorId;
    @Column(name = "note")
    private String note;
    @Column(name = "start_at")
    private LocalDateTime startAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}