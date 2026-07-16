package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.ChapterStatus;
import com.duong.backendservice.common.LessonStatus;
import com.duong.backendservice.dto.request.CreateLessonRequest;
import com.duong.backendservice.dto.request.UpdateLessonRequest;
import com.duong.backendservice.dto.response.CreateLessonResponse;
import com.duong.backendservice.dto.response.LessonDetailResponse;
import com.duong.backendservice.dto.response.PageResponse;
import com.duong.backendservice.entity.Chapter;
import com.duong.backendservice.entity.Lesson;
import com.duong.backendservice.mapper.LessonMapper;
import com.duong.backendservice.repository.ChapterRepository;
import com.duong.backendservice.repository.LessonRepository;
import com.duong.backendservice.repository.specification.LessonSpecification;
import com.duong.backendservice.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "LESSON-SERVICE")
public class LessonServiceImpl implements LessonService {
    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;

    @Override
    public CreateLessonResponse createLesson(CreateLessonRequest request) {
        Chapter chapter = chapterRepository.findById(request.chapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        if(chapter.getStatus() == ChapterStatus.INACTIVE){
            throw new RuntimeException("Chapter is inactive");
        }

        Lesson lesson = lessonMapper.toLesson(request);
        lesson.setChapter(chapter);
        lesson.setCreatedAt(Instant.now());
        lesson.setStatus(LessonStatus.ACTIVE);

        lessonRepository.save(lesson);
        log.info("Lesson {} is created", lesson.getLessonName());

        return lessonMapper.toCreateLessonResponse(lesson);
    }

    @Override
    public LessonDetailResponse updateLesson(String id, UpdateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        if(lesson.getStatus() == LessonStatus.INACTIVE){
            throw new RuntimeException("Lesson is inactive");
        }

        if(StringUtils.hasText(request.chapterId()) && !request.chapterId().equals(lesson.getChapter().getId())){
            Chapter chapter = chapterRepository.findById(request.chapterId())
                    .orElseThrow(() -> new RuntimeException("Chapter not found"));

            if(chapter.getStatus() == ChapterStatus.INACTIVE){
                throw new RuntimeException("Chapter is inactive");
            }

            lesson.setChapter(chapter);
        }

        lessonMapper.updateLesson(request, lesson);
        lesson.setUpdatedAt(Instant.now());
        lessonRepository.save(lesson);

        return lessonMapper.toLessonDetailResponse(lesson);
    }

    @Override
    public LessonDetailResponse getLessonById(String id) {
        return lessonRepository.findById(id)
                .map(lessonMapper::toLessonDetailResponse)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @Override
    public void deleteLesson(String id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        lesson.setStatus(LessonStatus.INACTIVE);
        lesson.setUpdatedAt(Instant.now());
        lessonRepository.save(lesson);

        log.info("Lesson {} is deleted", lesson.getId());
    }

    @Override
    public PageResponse<LessonDetailResponse> getLessons(int page, int size, String lessonName) {
        if(page <= 0){
            page = 1;
        }

        if(size <= 0 || size > 20){
            size = 20;
        }

        Specification<Lesson> lessonSpecification = Specification.allOf(
                LessonSpecification.hasName(lessonName)
        );

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Lesson> lessonPage = lessonRepository.findAll(lessonSpecification, pageable);
        List<Lesson> lessons = lessonPage.getContent();
        List<LessonDetailResponse> content = lessons.stream()
                .map(lessonMapper::toLessonDetailResponse)
                .toList();

        return PageResponse.<LessonDetailResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(lessonPage.getTotalPages())
                .totalElements(lessonPage.getTotalElements())
                .content(content)
                .build();
    }
}
