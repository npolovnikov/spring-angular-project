package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.dfsystems.spring.tutorial.generated.tables.daos.AppUserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;

@Service
@AllArgsConstructor
public class UserService {
    private AppUserDao appUserDao;
    private MappingService mappingService;
    private UserContext userContext;

    public AppUser getUserByLogin(String login){
        return appUserDao.fetchOptional(APP_USER.LOGIN, login)
                .orElse(null);
    }

    public boolean checkPassword(String login, String password) {
        AppUser user = getUserByLogin(login);
        if (user == null) {
            return false;
        }

        //ДЗ Добавить соль к паролю. Соль хранить в application.yml
        String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes())
                .toUpperCase();

        return md5Hex.equals(user.getPasswordHash());
    }

    public UserDto getCurrentUser() {
        return mappingService.map(userContext.getUser(), UserDto.class);
    }

    public void login(String login) {
        AppUser user = getUserByLogin(login);
        user.setLastLoginDate(LocalDateTime.now());
        user.setIsActive(true);

        appUserDao.update(user);
    }
}
