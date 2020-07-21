package ru.dfsystems.spring.tutorial.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto implements Serializable {
    private String login;
    private String password;
}
