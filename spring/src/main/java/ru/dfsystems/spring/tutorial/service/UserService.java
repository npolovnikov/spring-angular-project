package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dao.UserListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.mapping.RoomMapper;
import ru.dfsystems.spring.tutorial.mapping.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
@AllArgsConstructor
public class UserService {

    private UserDaoImpl userDao;
    private UserListDao userListDao;

    public Page<UserListDto> getUsersByParams(PageParams<UserParams> pageParams) {
        Page<User> page = userListDao.list(pageParams);
        List<UserListDto> list = UserMapper.USER_MAPPER.userListToUserListDto(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(UserDto userDto) {
        userDao.create(UserMapper.USER_MAPPER.userDtoToUser(userDto));
    }

    public UserDto get(Integer idd) {
       return UserMapper.USER_MAPPER.userToUserDto(userDao.getActiveByIdd(idd));
    }

    @Transactional
    public void delete(Integer idd) {
        User user = userDao.getActiveByIdd(idd);
        user.setDeleteDate(LocalDateTime.now());
        userDao.update(user);
    }

    public UserDto update(Integer idd, UserDto userDto) {
        User user = userDao.getActiveByIdd(idd);
        if (user == null) {
            throw new RuntimeException("ошибка");
        }
        user.setDeleteDate(LocalDateTime.now());
        userDao.update(user);

        User newUser = UserMapper.USER_MAPPER.userDtoToUser(userDto);
        newUser.setIdd(user.getIdd());
        userDao.create(newUser);
        return UserMapper.USER_MAPPER.userToUserDto(newUser);
    }

}
