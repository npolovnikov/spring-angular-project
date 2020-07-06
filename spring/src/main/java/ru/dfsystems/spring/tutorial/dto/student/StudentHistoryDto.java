package ru.dfsystems.spring.tutorial.dto.student;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentHistoryDto extends BaseHistoryDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String contacts;
    private LocalDateTime birthDate;
    private String status;
}
