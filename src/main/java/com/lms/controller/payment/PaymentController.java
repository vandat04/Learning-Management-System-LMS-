package com.lms.controller.payment;

import com.lms.dto.response.payment.CreatePaymentResponse;
import com.lms.service.core.payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.webhooks.WebhookData;

@RestController
@RequestMapping("/api/auth/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/success")
    public ResponseEntity<?> paymentSuccess(){
        return ResponseEntity.ok("Payment Successfully!!!");
    }

    @GetMapping("/fail")
    public ResponseEntity<?> paymentFail(){
        return ResponseEntity.ok("Payment Fail!!!");
    }

    @PostMapping("/create")
    public CreatePaymentLinkResponse payment(){
        return paymentService.createPaymentTest();
    }

    @PostMapping("/payos/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String request) {
        paymentService.handlePayOSWebhook(request);
        return ResponseEntity.ok("Payment oke!!!");
    }

}
