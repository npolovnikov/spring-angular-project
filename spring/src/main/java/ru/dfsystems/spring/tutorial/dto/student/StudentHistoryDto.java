package ru.dfsystems.spring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.dfsystems.spring.tutorial.dto.dto.BaseHistoryDto;

import java.time.LocalDateTime;

public class StudentHistoryDto extends BaseHistoryDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime birthDate;
}
