package com.duong.backendservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COURSE_NOT_FOUND(404, "Course not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(409, "User already existed", HttpStatus.CONFLICT),
    COURSE_INACTIVE(409, "Course is inactive", HttpStatus.CONFLICT),

    CHAPTER_NOT_FOUND(404, "Chapter not found", HttpStatus.NOT_FOUND),
    CHAPTER_INACTIVE(409, "Chapter is inactive", HttpStatus.CONFLICT),

    LESSON_NOT_FOUND(404, "Lesson not found", HttpStatus.NOT_FOUND),
    LESSON_INACTIVE(409, "Lesson is inactive", HttpStatus.CONFLICT)
    ;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

}
