package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseDto extends BaseDto {
    private String name;
    private String description;
    private Integer maxCountStudent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
