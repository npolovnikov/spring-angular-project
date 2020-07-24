package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import ru.dfsystems.spring.tutorial.dao.list.UserListDao;
import ru.dfsystems.spring.tutorial.dao.standard.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.standard.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.standard.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.user.AuthUserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.mapping.MappingService;
import ru.dfsystems.spring.tutorial.security.AuthUserContext;
import ru.dfsystems.spring.tutorial.security.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;
import static ru.dfsystems.spring.tutorial.security.CookieUtils.LOGIN_COOKIE_NAME;

@Service
public class UserService extends BaseService<UserListDto, UserDto, UserParams, User> {

    private AuthUserContext authUserContext;
    private UserDaoImpl userDao;
    private TeacherDaoImpl teacherDao;
    private StudentDaoImpl studentDao;
    private MappingService mappingService;

    public UserService(UserListDao userListDao, UserDaoImpl userDao, MappingService mappingService, AuthUserContext authUserContext) {
        super(mappingService, userListDao, userDao, UserListDto.class, UserDto.class, User.class);
        this.mappingService = mappingService;
        this.userDao = userDao;
        this.authUserContext = authUserContext;
    }

    public User getUserByLogin(String login){
        return userDao.fetchOptional(USER.LOGIN, login)
                .orElse(null);
    }

    private boolean checkPassword(String login, String password) {
        User user = getUserByLogin(login);
        if (user == null) {
            return false;
        }

        String md5Hex = getHashSum(password);

        return md5Hex.equals(user.getPassword());
    }

    public User getCurrentUser() {
        return authUserContext.getUser();
    }

    private String getHashSum(String password){
        return DigestUtils.md5DigestAsHex(password.concat(authUserContext.getHashKey()).getBytes())
                .toUpperCase();
    }

    private void login(String login) {
        User user = getUserByLogin(login);
        user.setLastLoginDate(LocalDateTime.now());
        user.setIsActive(true);

        userDao.update(user);
    }

    @Transactional
    public boolean doLogin(AuthUserDto authUserDto, HttpServletResponse response) {
        if (checkPassword(authUserDto.getLogin(), authUserDto.getPassword())) {
            Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, authUserDto.getLogin());
            cookie.setMaxAge(6 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            login(authUserDto.getLogin());
            return true;
        }
        return false;
    }

    @Transactional
    public void doLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, CookieUtils.extractLoginCookie(request).getValue());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    @Transactional
    public void doRegister(UserDto userDto, HttpServletResponse response) throws IOException {
        if(userDto.getStatus().equals("teacher")) {
            teacherDao.create(mappingService.map(userDto, Teacher.class));
        }
        if(userDto.getStatus().equals("student")) {
            studentDao.create(mappingService.map(userDto, Student.class));
        }

        User user = mappingService.map(userDto, User.class);
        user.setPassword(getHashSum(userDto.getPassword()));

        userDao.create(user);
    }
}
