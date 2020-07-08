package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.service.UserService;

@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class UserController extends BaseController<UserListDto, UserDto, UserParams, User> {

    private UserService userService;

    @Autowired
    public UserController(UserService service) {
        super(service);
        userService = service;
    }
}