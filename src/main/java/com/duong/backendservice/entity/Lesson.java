package com.duong.backendservice.entity;

import com.duong.backendservice.common.LessonFormat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String lessonName;

    private String description;

    @Enumerated(EnumType.STRING)
    private LessonFormat lessonFormat;

    private String videoUrl;

    private Boolean isFreePreview;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
}
