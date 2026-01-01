package com.lms.dto.request.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdminCreateUpdatePlanRequest {
    private String name;
    private Integer durationMonth;
    private BigDecimal price;
    private Boolean isActive;
}
