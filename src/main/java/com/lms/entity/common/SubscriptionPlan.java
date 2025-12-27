package com.lms.entity.common;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_plans")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscriptionPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration_months")
    private Integer durationMonths;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "is_active")
    private Boolean isActive;
}

