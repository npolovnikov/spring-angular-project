package ru.dfsystems.spring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseList;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StudentDto extends BaseDto<StudentHistory>{
    private String firstName;
    private String lastName;
    private String middleName;
    private String passport;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private List<CourseList> courses;
}
