package ru.dfsystems.spring.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static ru.dfsystems.spring.tutorial.security.CookieUtils.LOGIN_COOKIE_NAME;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody AuthDto authDto, HttpServletResponse response) {
        if (!doLogin(authDto, response)){
            throw new RuntimeException("Неверный логин или пароль");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        userService.logout();
    }

    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    private boolean doLogin(AuthDto authDto, HttpServletResponse response) {
        if (userService.checkPassword(authDto.getLogin(), authDto.getPassword())) {
            Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, authDto.getLogin());
            cookie.setMaxAge(6 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            userService.login(authDto.getLogin());
            return true;
        }
        return false;
    }

}
