package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.UserListDao;
import ru.dfsystems.spring.tutorial.dao.standard.UserDaoImpl;
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

    private UserListDao userListDao;
    private UserDaoImpl userDao;
    private MappingService mappingService;

    @Transactional
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public Page<UserListDto> getList(PageParams<UserParams> pageParams) {
        Page<User> page = userListDao.getSortedList(pageParams);
        List<UserListDto> list = mappingService.mapList(page.getList(), UserListDto.class);

        return new Page<>(list, page.getTotalCount());
    }
}
