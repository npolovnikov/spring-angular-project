package ru.student.studentSpring.tutorial.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonListDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String extraInstruments;

    private String lessonDateStart;
    private String lessonDateEnd;
}
