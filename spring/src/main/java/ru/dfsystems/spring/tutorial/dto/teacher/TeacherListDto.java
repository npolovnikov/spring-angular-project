package ru.dfsystems.spring.tutorial.dto.teacher;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Project spring-angular-project
 * Created by End on июнь, 2020
 */
@Getter
@Setter
public class TeacherListDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private LocalDateTime birthDate;
    private String status;

}
