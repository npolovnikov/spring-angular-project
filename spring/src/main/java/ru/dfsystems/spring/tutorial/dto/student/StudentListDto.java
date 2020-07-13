package ru.dfsystems.spring.tutorial.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentListDto extends BaseListDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private String birthDate;
}
