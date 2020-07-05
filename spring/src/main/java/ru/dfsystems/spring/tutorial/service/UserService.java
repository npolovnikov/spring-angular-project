package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserDaoImpl userDao;

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public Page<User> getUsersByParams(PageParams<UserParams> pageParams) {
        return userDao.getUsersByParams(pageParams);
    }
}
