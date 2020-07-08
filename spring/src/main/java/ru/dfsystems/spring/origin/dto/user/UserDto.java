package ru.dfsystems.spring.origin.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.origin.dto.BaseDto;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.origin.dto.student.StudentListDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.origin.generated.tables.Student;

import java.time.LocalDateTime;
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
