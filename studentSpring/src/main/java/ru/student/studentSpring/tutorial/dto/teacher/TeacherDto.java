package ru.student.studentSpring.tutorial.dto.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class TeacherDto extends BaseDto<TeacherHistoryDto> {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private String status;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthDate;
}
