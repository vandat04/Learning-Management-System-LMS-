package com.lms.entity.common;

import com.lms.entity.auth.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "instructor_subscriptions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorSubscription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne @JoinColumn(name = "instructor_id")
    private User instructor;
    @ManyToOne @JoinColumn(name = "plan_id")
    private SubscriptionPlan plan;
    private LocalDateTime createdAt = LocalDateTime.now();
}

