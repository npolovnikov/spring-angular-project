package ru.student.studentSpring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;

@Setter
@Getter
public class StudentHistoryDto extends BaseHistoryDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthDate;
}
