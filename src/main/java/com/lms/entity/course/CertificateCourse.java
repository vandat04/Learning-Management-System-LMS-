package com.lms.entity.course;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CertificateCourse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "certificate_url")
    private String certificateUrl;
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
}