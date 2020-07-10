package ru.dfsystems.spring.tutorial.service;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Users;
import ru.dfsystems.spring.tutorial.listDao.UserListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service

public class UserService extends BaseService<UserListDto, UserDto, UserParams, Users> {
    private UserDaoImpl userDao;
    private UserListDao userListDao;
    private MappingService mappingService;


    public UserService(UserDaoImpl userDao, UserListDao userListDao, MappingService mappingService) {
        super(mappingService, userListDao, userDao, UserListDto.class, UserDto.class, Users.class);
        this.userDao = userDao;
        this.userListDao = userListDao;
        this.mappingService = mappingService;
    }
}