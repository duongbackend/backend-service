package com.duong.backendservice.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Set<Lesson> lessons;
}
