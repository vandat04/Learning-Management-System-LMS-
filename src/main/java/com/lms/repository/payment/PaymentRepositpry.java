package com.lms.repository.payment;

import com.lms.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepositpry extends JpaRepository<Payment, Integer> {
    Payment findByOrderCode(Long orderCode);
}
