package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.user.AuthUserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.security.AuthUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class AuthUserController {
    private AuthUserService authUserService;

    @PostMapping("/login")
    public void login(@RequestBody AuthUserDto authUserDto, HttpServletResponse response) {
        if (!authUserService.doLogin(authUserDto, response)) {
            throw new RuntimeException("Неверный логин или пароль");
        }
    }

    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return authUserService.getCurrentUser();
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authUserService.doLogout(request, response);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserDto userDto) {
        authUserService.doRegister(userDto);
    }
}
