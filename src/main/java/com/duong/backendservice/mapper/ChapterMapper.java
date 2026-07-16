package com.duong.backendservice.mapper;

import com.duong.backendservice.dto.request.CreateChapterRequest;
import com.duong.backendservice.dto.request.UpdateChapterRequest;
import com.duong.backendservice.dto.response.ChapterDetailResponse;
import com.duong.backendservice.dto.response.CreateChapterResponse;
import com.duong.backendservice.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChapterMapper {
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "slug", ignore = true)
    Chapter toChapter(CreateChapterRequest request);

    CreateChapterResponse toCreateChapterResponse(Chapter chapter);

    ChapterDetailResponse toChapterDetailResponse(Chapter chapter);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "slug", ignore = true)
    void updateChapter(UpdateChapterRequest request, @MappingTarget Chapter chapter);
}
