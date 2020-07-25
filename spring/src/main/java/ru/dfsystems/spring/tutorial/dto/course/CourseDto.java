package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CourseDto extends BaseDto<CourseHistoryDto> {
    private String name;
    private String description;
    private String teacherId;
    private String maxCountStudent;
//    private String startDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<CourseListDto> courses;
}
