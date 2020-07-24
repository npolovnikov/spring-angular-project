package ru.dfsystems.spring.tutorial.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Дто для логина и пароля, получаемых с фронта
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto implements Serializable {
    private String login;
    private String password;
}