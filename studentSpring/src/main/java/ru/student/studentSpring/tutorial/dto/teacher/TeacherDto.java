package ru.student.studentSpring.tutorial.dto.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseDto;

import java.time.LocalDateTime;

@Setter
@Getter
public class TeacherDto extends BaseDto<TeacherHistoryDto> {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String passport;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime birth_date;
}
