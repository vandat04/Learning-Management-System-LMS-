package com.lms.dto.response.payment;

import lombok.Data;

@Data
public class CreatePaymentResponse {
    private String checkoutUrl;
    private Long orderCode;
    private Long amount;
    private String status;
    private Long expiredAt;
}
