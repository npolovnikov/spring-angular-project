package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Getter
@Setter
public class CourseDto extends BaseDto {

    private String name;
    private String description;
    private Integer teacher_idd;
    private Integer max_count_student;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String status;

    private List<StudentListDto> students;
}
