package ru.dfsystems.spring.tutorial.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.dto.BaseListDto;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserListDto extends BaseListDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String status;
    private int courseId;
    private String login;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deleteDate;
}
