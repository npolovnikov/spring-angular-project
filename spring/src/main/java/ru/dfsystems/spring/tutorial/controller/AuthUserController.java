package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.user.AuthUserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class AuthUserController {
    private UserService authUserService;

    @PostMapping("/login")
    public void login(@RequestBody AuthUserDto authUserDto, HttpServletResponse response) {
        if (!authUserService.doLogin(authUserDto, response)) {
            throw new RuntimeException("Неверный логин или пароль");
        }
    }

    @GetMapping("/current")
    public User getCurrentUser() {
        return authUserService.getCurrentUser();
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authUserService.doLogout(request, response);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserDto userDto, HttpServletResponse response) throws IOException {
        authUserService.doRegister(userDto, response);
    }
}
