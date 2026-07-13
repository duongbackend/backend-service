package com.duong.backendservice.repository.specification;

import com.duong.backendservice.entity.Course;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class CourseSpecification {
    public static Specification<Course> hasName(String name){
        return ((root, _, criteriaBuilder) -> {
            if(!StringUtils.hasText(name)){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        });
    }

    public static Specification<Course> from(BigDecimal from){
        return ((root, _, criteriaBuilder) -> {
            if(from == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), from);
        });
    }

    public static Specification<Course> to(BigDecimal to){
        return ((root, _, criteriaBuilder) -> {
            if(to == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), to);
        });
    }
}
