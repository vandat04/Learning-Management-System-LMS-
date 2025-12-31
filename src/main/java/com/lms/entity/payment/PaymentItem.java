package com.lms.entity.payment;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payment_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "plan_id")
    private Integer planId;
    @Column(name = "price")
    private BigDecimal price;
}
