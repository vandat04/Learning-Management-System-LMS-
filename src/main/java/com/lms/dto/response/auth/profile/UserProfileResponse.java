package com.lms.dto.response.auth.profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class UserProfileResponse {
    private Integer userId;
    private String gmail;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String bio;
    private BigDecimal ratingAvg;
    private Integer numberReview;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
