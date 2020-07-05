package ru.dfsystems.spring.tutorial.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserListDto extends BaseListDto {

    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime birthDate;

    private String status;
}
