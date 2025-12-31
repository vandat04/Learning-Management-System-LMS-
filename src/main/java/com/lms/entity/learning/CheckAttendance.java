package com.lms.entity.learning;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_attendance")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckAttendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "class_room_id")
    private Integer classRoomId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
