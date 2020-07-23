package ru.dfsystems.spring.tutorial.dto.teacher;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

@Getter
@Setter
public class TeacherListDto extends BaseListDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String status;
}
