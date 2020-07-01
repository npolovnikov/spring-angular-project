package ru.dfsystems.spring.tutorial.dto.student;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июнь, 2020
 */
@Getter
@Setter
public class StudentDto extends BaseDto<BaseHistoryDto> {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private LocalDateTime birthDate;
    private String status;
    private List<CourseListDto> courses;
}
