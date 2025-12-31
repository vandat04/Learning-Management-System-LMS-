package com.lms.entity.interaction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evidences")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evidence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "complaints_id")
    private Integer complaintsId;
    @Column(name = "evidence_url")
    private String evidenceUrl;
}
