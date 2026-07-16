package com.duong.backendservice.repository.specification;

import com.duong.backendservice.entity.Lesson;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class LessonSpecification {
    public static Specification<Lesson> hasName(String lessonName){
        return ((root, _, criteriaBuilder) -> {
            if(!StringUtils.hasText(lessonName)){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("lessonName"), "%" + lessonName + "%");
        });
    }
}
