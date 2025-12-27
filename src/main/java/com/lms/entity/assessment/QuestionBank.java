package com.lms.entity.assessment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_bank")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionBank {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "lesson_id")
    private Integer lessonId;
    @Column(name = "question")
    private String question;
    @Column(name = "option_a")
    private String optionA;
    @Column(name = "option_b")
    private String optionB;
    @Column(name = "option_c")
    private String optionC;
    @Column(name = "option_d")
    private String optionD;
    @Column(name = "correct_option")
    private String correctOption;
}
