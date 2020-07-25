package ru.student.studentSpring.tutorial.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CourseDto extends BaseDto<CourseHistoryDto> {
    private String name;
    private String description;
    private Integer maxCountStudent;

    private List<TeacherListDto> teacher;

    private String startDate;

    private String endDate;

    private String status;
}
