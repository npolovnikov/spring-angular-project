package ru.dfsystems.spring.tutorial.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseHistoryDto extends BaseHistoryDto {

    private String name;
    private String description;
    private TeacherListDto teacher;
    private Integer maxCountStudent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
    private String status;

}



