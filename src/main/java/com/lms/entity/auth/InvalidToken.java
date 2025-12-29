package com.lms.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "invalid_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvalidToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_time")
    private Date expiryTime;
}
