package com.duong.backendservice.entity;

import com.duong.backendservice.common.CourseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "courses",
        indexes = @Index(columnList = "status", name = "course_status_idx")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;

    private Double hours;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    private Instant createdAt;

    private Instant updatedAt;
}
