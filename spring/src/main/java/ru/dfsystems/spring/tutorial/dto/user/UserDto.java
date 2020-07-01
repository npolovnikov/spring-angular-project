package ru.dfsystems.spring.tutorial.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Project spring-angular-project
 * Created by End on июнь, 2020
 */

@Getter
@Setter
public class UserDto extends BaseDto<BaseHistoryDto> {

    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private LocalDateTime birthDate;
    private String status;

}
