package com.lms.entity.payment;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "plan_id")
    private Integer planId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "payment_type")
    private Integer paymentType;
    @Column(name = "transaction_code")
    private String transactionCode;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}