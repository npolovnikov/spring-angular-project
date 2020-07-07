package ru.dfsystems.spring.tutorial.dto.teacher;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.dto.BaseDto;

@Getter
@Setter
public class TeacherDto extends BaseDto<TeacherHistoryDto> {

    private String firstName;
    private String middleName;
    private String lastName;
    private String status;
    private Integer courseIdd;
}
