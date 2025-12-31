package com.lms.repository.payment;

import com.lms.entity.payment.PaymentItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentItemRepository extends JpaRepository<PaymentItem, Integer> {
    List<PaymentItem> findByPaymentId(Integer paymentId);
}
