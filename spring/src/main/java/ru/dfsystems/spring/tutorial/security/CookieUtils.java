package ru.dfsystems.spring.tutorial.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
    /* просто имя куки*/
    public static final String LOGIN_COOKIE_NAME = "username";

    /* проверяет куку*/
    public static Cookie extractLoginCookie(HttpServletRequest request) {
        return extractCookie(request, LOGIN_COOKIE_NAME);
    }

    private static Cookie extractCookie(HttpServletRequest request, String cookieName) {
        if (request == null) {
            return null;
        }
        /*  запрос не умеет возвращать куку по имени, поэтому надо их  перебрать*/
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(cookieName)){
                    return cookie;
                }
            }
        }
        return null;
    }
}