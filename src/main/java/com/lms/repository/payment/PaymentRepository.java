package com.lms.repository.payment;

import com.lms.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByOrderCode(Long orderCode);

    @Query("""
    SELECT p FROM Payment p
    WHERE p.status = 1
      AND p.createdAt < :now
    """)
    List<Payment> findPendingExpired(LocalDateTime now);
}
