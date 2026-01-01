package com.lms.service.core.payment;

import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;

public interface PaymentService {
    CreatePaymentLinkResponse createSubscriptionPlanPayment(Integer planId);
    void handleSubPayOSWebhook(String request);
    void handleCancelPayment(Long orderCode);
    void handleQRExpiredPayments();
}
