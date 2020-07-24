package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.security.dto.AuthDto;
import ru.dfsystems.spring.tutorial.security.dto.UserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static ru.dfsystems.spring.tutorial.security.CookieUtils.LOGIN_COOKIE_NAME;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    /* получаем логини пароль*/
    /*  респонс, чтобы при успешном логине на фрон приходила кука*/
    @PostMapping("/login")
    public void login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        if (!doLogin(authDto, response)) {
            throw new RuntimeException("Неверный логин или пароль");
        }
    }

    /* на фронт передаем текущего пользователя (по куке) */
    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    private boolean doLogin(AuthDto authDto, HttpServletResponse response) {
        if (userService.checkPassword(authDto.getLogin(), authDto.getPassword())) {
            /*  создаем возвращаемую на фронт куку */
            Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, authDto.getLogin());
            /*  время жизни */
            cookie.setMaxAge(6 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            /* логин произошел успешно */
            userService.login(authDto.getLogin());
            return true;
        }
        return false;
    }

    //TODO ДЗ logout, чтобы кука стала больше не действительной
}