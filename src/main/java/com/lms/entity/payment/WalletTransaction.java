package com.lms.entity.payment;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WalletTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "instructor_id")
    private Integer instructorId;
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "type")
    private Integer type;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
