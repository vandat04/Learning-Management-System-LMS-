package com.lms.dto.request.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Data
public class AdminCreateSubscriptionPlanRequest {
    private String name;
    private Integer durationMonth;
    private BigDecimal price;
}
