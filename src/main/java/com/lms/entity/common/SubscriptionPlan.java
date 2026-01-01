package com.lms.entity.common;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class SubscriptionPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration_days")
    private Integer durationDays;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "is_active")
    private Boolean isActive;
}

