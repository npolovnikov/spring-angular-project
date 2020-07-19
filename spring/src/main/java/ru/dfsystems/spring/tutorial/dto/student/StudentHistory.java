package ru.dfsystems.spring.tutorial.dto.student;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

@Getter
@Setter
public class StudentHistory extends BaseHistoryDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String passport;
}
