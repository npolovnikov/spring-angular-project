package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.user.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dao.user.UserListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserDaoImpl userDao;
    private UserListDao userListDao;
    private MappingService mappingService;

    public Page<UserListDto> getUsersByParams(PageParams<UserParams> pageParams) {
        Page<User> page = userListDao.list(pageParams);
        List<UserListDto> list = mappingService.mapList(page.getList(),  UserListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
