package com.lms.dto.request.payment;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class CreatePaymentRequest {
    private Integer userId;
    private List<Integer> courseId;
    @Value("${payos.returnUrl}")
    private String returnUrl;
    @Value("${payos.cancelUrl}")
    private String cancelUrl;
}
