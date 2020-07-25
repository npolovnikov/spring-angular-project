package ru.student.studentSpring.tutorial.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonParams {
    private Integer id;
    private String name;
    private String description;
    private String orderBy;
    private String orderDir;

    private String lessonDateStart;

    private String lessonDateEnd;

}
