package ru.dfsystems.spring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.dto.BaseDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentDto extends BaseDto<StudentHistoryDto> {

    private String firstName;
    private String middleName;
    private String lastName;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime birthDate;
}
