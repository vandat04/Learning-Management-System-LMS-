package com.lms.service.core.payment;

import jakarta.servlet.http.HttpServletRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;

public interface PaymentService {
    CreatePaymentLinkResponse createPaymentTest();
    void handlePayOSWebhook(String request);
}
