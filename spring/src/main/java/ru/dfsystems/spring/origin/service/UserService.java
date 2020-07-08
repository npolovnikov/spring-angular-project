package ru.dfsystems.spring.origin.service;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.dfsystems.spring.origin.dao.UserDaoImpl;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.origin.dto.room.RoomListDto;
import ru.dfsystems.spring.origin.dto.room.RoomParams;
import ru.dfsystems.spring.origin.dto.student.StudentParams;
import ru.dfsystems.spring.origin.dto.user.UserDto;
import ru.dfsystems.spring.origin.dto.user.UserHistoryDto;
import ru.dfsystems.spring.origin.dto.user.UserListDto;
import ru.dfsystems.spring.origin.dto.user.UserParams;
import ru.dfsystems.spring.origin.generated.tables.pojos.Room;
import ru.dfsystems.spring.origin.generated.tables.pojos.Users;
import ru.dfsystems.spring.origin.generated.tables.pojos.Student;
import ru.dfsystems.spring.origin.listDao.UserListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserDaoImpl userDao;
    private UserListDao userListDao;
    private MappingService mappingService;

    public Page<UserListDto> getUsersByParams(PageParams<UserParams> pageParams) {
        Page <Users> page = userListDao.list(pageParams);
        List<UserListDto> list = mappingService.mapList(page.getList(), UserListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(UserDto userDto) {
        userDao.create(mappingService.map(userDto, Users.class));
    }

    public UserDto get(Integer idd) {
        UserDto dto = mappingService.map(userDao.getActiveByIdd(idd), UserDto.class);
        return dto;
    }

    public void delete(Integer idd) {
        Users users = userDao.getActiveByIdd(idd);
        users.setDeleteDate(LocalDateTime.now());
        userDao.update(users);
    }

    public UserDto update(Integer idd, UserDto userDto) {
        Users users = userDao.getActiveByIdd(idd);
        if (users == null){
            throw new RuntimeException("");
        }
        users.setDeleteDate(LocalDateTime.now());
        userDao.update(users);
        Users newUsers = mappingService.map(userDto, Users.class);
        newUsers.setIdd(users.getIdd());
        userDao.create(newUsers);
        return mappingService.map(users, UserDto.class);
    }

    public List<UserHistoryDto> getHistory(Integer idd) {
        return mappingService.mapList(userDao.getHistory(idd), UserHistoryDto.class);
    }
}