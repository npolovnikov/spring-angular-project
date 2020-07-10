package ru.dfsystems.spring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.Course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.BaseDto;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class StudentDto extends BaseDto<StudentHistoryDto> {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthDateStart;
    private List<CourseListDto> course;
}
