package ru.dfsystems.spring.tutorial.dto.Course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;

import java.time.LocalDateTime;


@Getter
@Setter
public class CourseDto extends BaseDto<CourseHistoryDto> {
    private String name;
    private String description;
    private String status;
    private Integer teacherId;
    private Integer maxCountStudent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateEnd;

}
