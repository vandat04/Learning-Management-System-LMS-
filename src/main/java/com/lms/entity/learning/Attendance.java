package com.lms.entity.learning;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"attendance_id","student_id"}))
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "attendance_id")
    private Integer attendanceId;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "is_present")
    private Boolean isPresent;
}
