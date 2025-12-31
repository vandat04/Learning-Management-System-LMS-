package com.lms.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "application_id")
    private Integer applicationId;
    @Column(name = "title")
    private String title;
    @Column(name = "document_url")
    private String documentUrl;
}