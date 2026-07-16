package com.duong.backendservice.repository;

import com.duong.backendservice.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String>, JpaSpecificationExecutor<Chapter> {
    boolean existsBySlug(String slug);

    Optional<Chapter> findBySlug(String slug);
}
