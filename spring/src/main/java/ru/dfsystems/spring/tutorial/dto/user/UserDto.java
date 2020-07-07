package ru.dfsystems.spring.tutorial.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.dto.BaseDto;

@Getter
@Setter
public class UserDto extends BaseDto<UserHistoryDto> {

    private String firstName;
    private String middleName;
    private String lastName;
    private String status;
    private int courseId;
    private String login;
    private String password;
}
