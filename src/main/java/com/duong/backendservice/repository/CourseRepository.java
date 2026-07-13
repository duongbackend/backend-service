package com.duong.backendservice.repository;

import com.duong.backendservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {
    boolean existsBySlug(String slug);

    Optional<Course> findBySlug(String slug);
}
