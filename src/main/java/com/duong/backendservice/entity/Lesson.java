package com.duong.backendservice.entity;

import com.duong.backendservice.common.LessonFormat;
import com.duong.backendservice.common.LessonStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    private Instant createdAt;

    private Instant updatedAt;
}
