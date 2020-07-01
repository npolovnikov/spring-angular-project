package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

/**
 * Project spring-angular-project
 * Created by End on июнь, 2020
 */

@Getter
@Setter
public class CourseListDto extends BaseListDto {

    private String name;
    private String description;
    private Integer teacher_idd;
    private Integer max_count_student;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String status;
}
