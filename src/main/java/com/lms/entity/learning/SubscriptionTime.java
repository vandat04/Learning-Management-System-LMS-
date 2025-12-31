package com.lms.entity.learning;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions_time")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionTime {
    @Id
    @Column(name = "instructor_id")
    private Integer instructorId;
    @Column(name = "end_date")
    private LocalDateTime endDate;
}


