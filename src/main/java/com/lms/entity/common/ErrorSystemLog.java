package com.lms.entity.common;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "error_properties_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorSystemLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "error")
    private String error;
    @Column(name = "active")
    private Integer active;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "solved_at")
    private LocalDateTime solvedAt;
}
