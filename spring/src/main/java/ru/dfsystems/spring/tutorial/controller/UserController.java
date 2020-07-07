package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private ModelMapper mapper = new ModelMapper();

    /**
     * Возвращает элементы, соответствующие параметрам
     */
    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<UserParams> pageParams) {
        Page<User> page = userService.getUsersByParams(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    private List<BaseListDto> mapper(List<User> allUsers) {
        return allUsers.stream()
                .map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}

