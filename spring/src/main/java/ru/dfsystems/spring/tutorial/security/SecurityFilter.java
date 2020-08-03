package ru.dfsystems.spring.tutorial.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.HOURS;
import static ru.dfsystems.spring.tutorial.security.CookieUtils.extractLoginCookie;

/**
 * прослойка между запросом и контроллером
 */
@Component
public class SecurityFilter implements Filter {
    /* публичные запросы без проверки фильтром */
    private String[] publicUriPatterns;
    private UserService userService;
    private UserContext userContext;

    public SecurityFilter(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }

    /* из конфига получаем publicUriPatterns*/
    @Override
    public void init(FilterConfig filterConfig) {
        String publicUriPatterns = filterConfig.getInitParameter("public");

        if (publicUriPatterns == null) {
            return;
        }
        /* разбваем вокруг запятой, после должна быт строка  */
        this.publicUriPatterns = publicUriPatterns.trim().split(",\\s");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        boolean filtered = applyFilter((HttpServletRequest) req, (HttpServletResponse) resp);

        if (filtered) {
            return;
        }

        chain.doFilter(req, resp);
    }

    private boolean applyFilter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /* получаем юрл запроса, убираем домен и порт  */
        String uri = req.getRequestURI().replaceFirst(req.getContextPath(), "");

        if (isPublic(uri)) {
            return false;
        }

        /* получаем куку из запроса куку */
        Cookie userCookie = extractLoginCookie(req);
        /* проверяем, авторизован ли пользователь */
        if (!isAuthRequest(userCookie)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return true;
        }

        return false;
    }

    /* проверяет, действительна ли кука */
    private boolean isAuthRequest(Cookie userCookie) {
        if (userCookie != null && userCookie.getValue() != null && !userCookie.getValue().isEmpty()) {
            var user = userService.getUserByLogin(userCookie.getValue());
            /* проверяем, что он залогинился в течении 6 часов с данного момента, не раньше */
            if (user != null && user.getIsActive() &&
                    user.getLastLoginDate().isAfter(LocalDateTime.now().minus(6, HOURS))) {
                userContext.setUser(user);
                return true;
            }
        }
        return false;
    }

    /* проверяем, что данный урл не публичный */
    private boolean isPublic(String uri) {
        if (uri == null || uri.isEmpty() || publicUriPatterns == null) {
            return false;
        }

        for (String pattern : publicUriPatterns) {
            /* если в паттерне звездочка, отрезаем лишнее */
            if (pattern.contains("*")) {
                if (pattern.startsWith("/") && uri.startsWith(pattern.replaceAll("\\*", ""))) {
                    return true;
                }
                if (pattern.startsWith("*") && uri.contains(pattern.replaceAll("\\*", ""))) {
                    return true;
                }
                /* если нет, должно быть полное совпадение */
            } else {
                if (uri.equals(pattern)) {
                    return true;
                }
            }
        }

        return false;
    }
}