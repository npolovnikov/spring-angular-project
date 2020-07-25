package ru.student.studentSpring.tutorial.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;

import java.time.LocalDateTime;

@Setter
@Getter
public class CourseHistoryDto extends BaseHistoryDto {

    private String name;
    private String status;

    private String startDate;

    private String endDate;
}
