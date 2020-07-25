package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseHistoryDto extends BaseHistoryDto {
    private String name;
    private String description;
    private String teacherId;
    private String maxCountStudent;
//    private String startDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
