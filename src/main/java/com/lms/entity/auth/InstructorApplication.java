package com.lms.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "instructor_application")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstructorApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "is_approved")
    private Integer isApproved;
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    @Column(name = "admin_feedback")
    private String adminFeedback;
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
}
