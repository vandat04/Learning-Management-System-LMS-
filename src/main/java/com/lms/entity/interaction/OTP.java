package com.lms.entity.interaction;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_reset_password")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "otp")
    private String otp;
    @Column(name = "start_at")
    private LocalDateTime start_at;
    @Column(name = "end_at")
    private LocalDateTime end_at;
}
