package com.lms.entity.common;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banned_keywords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BannedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "word")
    private String word;
}



