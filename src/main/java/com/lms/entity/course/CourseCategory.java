package com.lms.entity.course;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses_category")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "course_id")
    private Integer courseId;
}
