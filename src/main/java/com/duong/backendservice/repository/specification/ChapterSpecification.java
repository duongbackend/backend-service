package com.duong.backendservice.repository.specification;

import com.duong.backendservice.entity.Chapter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ChapterSpecification {
    public static Specification<Chapter> hasName(String chapterName){
        return ((root, _, criteriaBuilder) -> {
            if(!StringUtils.hasText(chapterName)){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("chapterName"), "%" + chapterName + "%");
        });
    }
}
