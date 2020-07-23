package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.dfsystems.spring.tutorial.dao.UserDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserCreateDto;
import ru.dfsystems.spring.tutorial.dto.user.UserList;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.AppUserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;

@Service
@AllArgsConstructor
public class UserService {
    private AppUserDao appUserDao;
    private UserDaoImpl userDao;
    private MappingService mappingService;
    private UserContext userContext;
    private SecurityDbConfig securityDbConfig;

    public AppUser getUserByLogin(String login){
        return appUserDao.fetchOptional(APP_USER.LOGIN, login)
                .orElse(null);
    }

    public boolean checkPassword(String login, String password) {
        AppUser user = getUserByLogin(login);
        if (user == null) {
            return false;
        }

        String md5Hex = DigestUtils.md5DigestAsHex((password + securityDbConfig.getSalt()).getBytes());

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

    public void logout() {
        AppUser user = getUserByLogin(userContext.getUser().getLogin());
        user.setIsActive(false);
        appUserDao.update(user);
    }

    public Page<UserList> list(PageParams<UserParams> pageParams) {
        Page<AppUser> page = userDao.list(pageParams);
        java.util.List<UserList> list = mappingService.mapList(page.getList(), UserList.class);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(UserCreateDto dto) {
        AppUser appUser = mappingService.map(dto, AppUser.class);
        appUser.setPasswordHash(DigestUtils.md5DigestAsHex((dto.getPassword() + securityDbConfig.getSalt()).getBytes()));
        appUser.setIsActive(false);
        userDao.create(appUser);
    }

    public void delete(Integer id) {
        appUserDao.deleteById(id);
    }
}
