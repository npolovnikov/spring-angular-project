package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserCreateDto;
import ru.dfsystems.spring.tutorial.dto.user.UserList;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.security.UserService;

@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
@Api(tags =  {"Пользователи"}, description = "API для пльзователей")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping("/list")
    public Page<UserList> getList(@RequestBody PageParams<UserParams> pageParams) {
        return service.list(pageParams);
    }

    @PostMapping
    public void create(@RequestBody UserCreateDto dto) {
        service.create(dto);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd) {
        service.delete(idd);
    }
}
