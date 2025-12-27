package com.lms.entity.interaction;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_groups")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatGroup {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
