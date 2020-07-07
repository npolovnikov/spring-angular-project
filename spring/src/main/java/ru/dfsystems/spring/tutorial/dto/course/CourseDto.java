package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;

import java.util.List;

@Getter
@Setter
public class CourseDto extends BaseDto<CourseHistoryDto> {

    private String name;
    private String description;
    private Integer teacherIdd;
    private Integer maxCountStudent;
    private String status;

    private List<LessonListDto> lessons;
}
