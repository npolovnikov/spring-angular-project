package ru.student.studentSpring.tutorial.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class CourseListDto extends BaseListDto {

    private String name;
    private String description;
    private Integer max_count_student;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end_date;
    private String status;
}
