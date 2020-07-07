package ru.dfsystems.spring.tutorial.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseHistoryDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessonDto extends BaseDto<CourseHistoryDto> {
    private String name;
    private String description;
    private Integer courseIdd;
    private Integer roomIdd;
    private String extraInstruments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lessonDateStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lessonDateEnd;
}
