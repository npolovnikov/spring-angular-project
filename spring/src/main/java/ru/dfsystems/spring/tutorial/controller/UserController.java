package ru.dfsystems.spring.tutorial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.UserService;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@RestController
@Tag(name = "Пользователь", description = "Api Пользователь")
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class UserController extends BaseController<UserListDto, UserDto, UserParams, User> {

    private UserService userService;

    @Autowired
    public UserController(BaseService<UserListDto, UserDto, UserParams, User> service, UserService userService) {
        super(service);
        this.userService = userService;
    }

    @PostMapping("/list")
    @Operation(summary = "Список пользователей", description = "", tags = {"user"})
    public Page<UserListDto> getList(@RequestBody PageParams<UserParams> params) {
        return userService.list(params);
    }

    @PostMapping
    @Operation(summary = "Добавить пользователя", description = "", tags = {"user"})
    public void create(@RequestBody UserDto userDto) {
        userService.create(userDto);
    }

    @GetMapping("/{idd}")
    @Operation(summary = "Информация о пользователе", description = "", tags = {"user"})
    public UserDto get(@PathVariable("idd") Integer idd) {
        return userService.get(idd);
    }

    @PatchMapping("/{idd}")
    @Operation(summary = "Обновить информацию о пользователе", description = "", tags = {"user"})
    public UserDto update(@PathVariable("idd") Integer idd, @RequestBody UserDto userDto) {
        return userService.update(idd, userDto);
    }

    @DeleteMapping("/{idd}")
    @Operation(summary = "Удалить пользователя", description = "", tags = {"user"})
    public void delete(@PathVariable("idd") Integer idd) {
        userService.delete(idd);
    }

}
