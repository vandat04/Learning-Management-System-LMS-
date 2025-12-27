package com.lms.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "instructor_wallets")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InstructorWallet {
    @Id
    @Column(name = "instructor_id")
    private Integer instructorId;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "total_earned")
    private BigDecimal totalEarned;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
