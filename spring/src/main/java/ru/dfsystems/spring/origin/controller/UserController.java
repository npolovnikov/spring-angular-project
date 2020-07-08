package ru.dfsystems.spring.origin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.origin.dto.user.UserDto;
import ru.dfsystems.spring.origin.dto.user.UserHistoryDto;
import ru.dfsystems.spring.origin.dto.user.UserListDto;
import ru.dfsystems.spring.origin.dto.user.UserParams;
import ru.dfsystems.spring.origin.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/list")
    public Page<UserListDto> getList(@RequestBody PageParams<UserParams> pageParams) {
        return userService.getUsersByParams(pageParams);
    }

    @PostMapping
    public void create(@RequestBody UserDto userDto){
        userService.create(userDto);
    }

    @GetMapping("/{idd}")
    public UserDto get(@PathVariable("idd") Integer idd){
        return userService.get(idd);
    }

    @PatchMapping("/{idd}")
    public UserDto update(@PathVariable("idd") Integer idd, @RequestBody UserDto userDto){
        return userService.update(idd, userDto);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd){
        userService.delete(idd);
    }

    @GetMapping("/{idd}/history")
    public List<UserHistoryDto> getHistory(@PathVariable("idd") Integer idd){
        return userService.getHistory(idd);
    }

}