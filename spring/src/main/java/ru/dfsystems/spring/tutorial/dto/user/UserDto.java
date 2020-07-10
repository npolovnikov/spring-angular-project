package ru.dfsystems.spring.tutorial.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;

import java.util.List;


@Getter
@Setter
public class UserDto extends BaseDto<UserHistoryDto> {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private String status;
    private String birthDateStart;
    private List<TeacherListDto> teacher;
    private List<StudentListDto> student;
}
