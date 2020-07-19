package ru.dfsystems.spring.tutorial.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String login;
    private String fio;
    private String password;
}
