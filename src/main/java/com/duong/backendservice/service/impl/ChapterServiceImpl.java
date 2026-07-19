package com.duong.backendservice.service.impl;

import com.duong.backendservice.common.ChapterStatus;
import com.duong.backendservice.dto.request.CreateChapterRequest;
import com.duong.backendservice.dto.request.UpdateChapterRequest;
import com.duong.backendservice.dto.response.ChapterDetailResponse;
import com.duong.backendservice.dto.response.CreateChapterResponse;
import com.duong.backendservice.dto.response.PageResponse;
import com.duong.backendservice.entity.Chapter;
import com.duong.backendservice.entity.Course;
import com.duong.backendservice.exception.AppException;
import com.duong.backendservice.exception.ErrorCode;
import com.duong.backendservice.mapper.ChapterMapper;
import com.duong.backendservice.repository.ChapterRepository;
import com.duong.backendservice.repository.CourseRepository;
import com.duong.backendservice.repository.specification.ChapterSpecification;
import com.duong.backendservice.service.ChapterService;
import com.github.slugify.Slugify;
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
@Slf4j(topic = "CHAPTER-SERVICE")
public class ChapterServiceImpl implements ChapterService {
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;
    private final ChapterRepository chapterRepository;
    private final Slugify slugify;

    @Override
    public CreateChapterResponse createChapter(CreateChapterRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        String name = request.chapterName();
        String slug = slugify.slugify(name);
        if(chapterRepository.existsBySlug(slug)){
            slug = slug + "-" + System.currentTimeMillis();
        }

        Chapter chapter = chapterMapper.toChapter(request);
        chapter.setSlug(slug);
        chapter.setCourse(course);
        chapter.setCreatedAt(Instant.now());
        chapter.setStatus(ChapterStatus.ACTIVE);

        chapterRepository.save(chapter);
        log.info("Chapter {} is created", chapter.getChapterName());

        CreateChapterResponse response = chapterMapper.toCreateChapterResponse(chapter);
        response.setCreatedAt(chapter.getCreatedAt());
        return response;
    }

    @Override
    public ChapterDetailResponse updateChapter(String id, UpdateChapterRequest request) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        if(chapter.getStatus() == ChapterStatus.INACTIVE){
            throw new AppException(ErrorCode.CHAPTER_INACTIVE);
        }

        if(StringUtils.hasText(request.chapterName()) && !request.chapterName().equals(chapter.getChapterName())){
            String slug = slugify.slugify(request.chapterName());
            if(chapterRepository.existsBySlug(slug)){
                slug = slug + "-" + System.currentTimeMillis();
            }
            chapter.setSlug(slug);
        }

        if(StringUtils.hasText(request.courseId()) && !request.courseId().equals(chapter.getCourse().getId())){
            Course course = courseRepository.findById(request.courseId())
                    .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
            chapter.setCourse(course);
        }

        chapterMapper.updateChapter(request, chapter);
        chapter.setUpdatedAt(Instant.now());
        chapterRepository.save(chapter);

        return chapterMapper.toChapterDetailResponse(chapter);
    }

    @Override
    public ChapterDetailResponse getChapterBySlug(String slug) {
        return chapterRepository.findBySlug(slug)
                .map(chapterMapper::toChapterDetailResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));
    }

    @Override
    public void deleteChapter(String id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_FOUND));

        chapter.setStatus(ChapterStatus.INACTIVE);
        chapter.setUpdatedAt(Instant.now());
        chapterRepository.save(chapter);

        log.info("Chapter {} is deleted", chapter.getId());
    }

    @Override
    public PageResponse<ChapterDetailResponse> getChapters(int page, int size, String chapterName) {
        if(page <= 0){
            page = 1;
        }

        if(size <= 0 || size > 20){
            size = 20;
        }

        Specification<Chapter> chapterSpecification = Specification.allOf(
                ChapterSpecification.hasName(chapterName)
        );

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Chapter> chapterPage = chapterRepository.findAll(chapterSpecification, pageable);
        List<Chapter> chapters = chapterPage.getContent();
        List<ChapterDetailResponse> content = chapters.stream()
                .map(chapterMapper::toChapterDetailResponse)
                .toList();

        return PageResponse.<ChapterDetailResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(chapterPage.getTotalPages())
                .totalElements(chapterPage.getTotalElements())
                .content(content)
                .build();
    }
}
