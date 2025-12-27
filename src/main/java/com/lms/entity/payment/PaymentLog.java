package com.lms.entity.payment;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_logs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "provider")
    private String provider;
    @Column(name = "event_type")
    private String eventType;
    @Column(name = "raw_response")
    private String rawResponse;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
