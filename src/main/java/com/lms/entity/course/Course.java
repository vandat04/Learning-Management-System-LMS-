package com.lms.entity.course;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "instructor_id")
    private Integer instructorId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "status")
    private Integer status;
    @Column(name = "rating_avg")
    private BigDecimal ratingAvg;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "approved_by")
    private Integer approvedBy;
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
