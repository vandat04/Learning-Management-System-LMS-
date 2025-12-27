package com.lms.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfile {
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "bio")
    private String bio;
    @Column(name = "rating_avg")
    private BigDecimal ratingAvg;
    @Column(name = "number_review")
    private Integer numberReview;
}