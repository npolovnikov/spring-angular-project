package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.user.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dao.user.UserListDao;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class UserService extends BaseService<UserListDto, UserDto, UserParams, User> {
    @Autowired
    public UserService(UserListDao userListDao,
                       UserDaoImpl userDao,
                       MappingService mappingService) {
        super(mappingService, userListDao, userDao, UserListDto.class, UserDto.class, User.class);
    }
}
