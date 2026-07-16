package com.duong.backendservice.entity;

import com.duong.backendservice.common.ChapterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "chapters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String chapterName;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;

    @Enumerated(EnumType.STRING)
    private ChapterStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "chapter")
    private Set<Lesson> lessons;

    private Instant createdAt;

    private Instant updatedAt;
}
