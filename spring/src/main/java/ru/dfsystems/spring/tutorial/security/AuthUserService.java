package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.dfsystems.spring.tutorial.dto.user.AuthUserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.security.AuthUserDaoImpl;

import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;
import static ru.dfsystems.spring.tutorial.security.CookieUtils.LOGIN_COOKIE_NAME;

@Service
@AllArgsConstructor
public class AuthUserService {
    private AuthUserDaoImpl appUserDao;
    private MappingService mappingService;
    private AuthUserContext authUserContext;


    public AppUser getUserByLogin(String login){
        return appUserDao.fetchOptional(APP_USER.LOGIN, login)
                .orElse(null);
    }

    public boolean checkPassword(String login, String password) {
        AppUser user = getUserByLogin(login);
        if (user == null) {
            return false;
        }

        String md5Hex = getHashSum(password);

        return md5Hex.equals(user.getPasswordHash());
    }

    public UserDto getCurrentUser() {
        return mappingService.map(authUserContext.getUser(), UserDto.class);
    }

    private String getHashSum(String password){
        return DigestUtils.md5DigestAsHex(password.concat(authUserContext.getHashKey()).getBytes())
                .toUpperCase();
    }


    private void login(String login) {
        AppUser user = getUserByLogin(login);
        user.setLastLoginDate(LocalDateTime.now());
        user.setIsActive(true);

        appUserDao.update(user);
    }

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

    public void doLogout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, CookieUtils.extractLoginCookie(request).getValue());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    public void doRegister(UserDto userDto){

        userDto.setCreateDate(LocalDateTime.now());
//TODO Mapping Service
        AppUser user = new AppUser();
        user.setLastLoginDate(LocalDateTime.now());
        user.setPasswordHash(getHashSum(userDto.getPassword()));
        user.setIsActive(true);

        appUserDao.update(user);
    }
}
