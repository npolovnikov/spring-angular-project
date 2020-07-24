package ru.dfsystems.spring.tutorial.security;

import org.springframework.stereotype.Component;
import ru.dfsystems.spring.tutorial.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.HOURS;
import static ru.dfsystems.spring.tutorial.security.CookieUtils.extractLoginCookie;

@Component
public class SecurityFilter implements Filter {
    private String[] publicUriPatterns;

//    private AuthUserService authUserService;
    private UserService userService;
    private AuthUserContext authUserContext;

    public SecurityFilter(UserService userService, AuthUserContext authUserContext) {
        this.userService = userService;
        this.authUserContext = authUserContext;
    }

    @Override
    public void init(FilterConfig filterConfig){
        String publicUriPatterns = filterConfig.getInitParameter("public");

        if (publicUriPatterns == null) {
            return;
        }

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
        String uri = req.getRequestURI().replaceFirst(req.getContextPath(), "");

        if (isPublic(uri)){
            return false;
        }

        Cookie userCookie = extractLoginCookie(req);
        if (!isAuthRequest(userCookie)){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return true;
        }

        return false;
    }

    private boolean isAuthRequest(Cookie userCookie) {
        if (userCookie != null && userCookie.getValue() != null && !userCookie.getValue().isEmpty()) {
//            var user = authUserService.getUserByLogin(userCookie.getValue());
            var user = userService.getUserByLogin(userCookie.getValue());
            if (user != null && user.getIsActive() &&
                    user.getLastLoginDate().isAfter(LocalDateTime.now().minus(6, HOURS))) {
                authUserContext.setUser(user);
                return true;
            }
        }
        return false;
    }

    private boolean isPublic(String uri) {
        if (uri == null || uri.isEmpty() || publicUriPatterns == null){
            return false;
        }

        for (String pattern: publicUriPatterns){
            if (pattern.contains("*")){
                if (pattern.startsWith("/") && uri.startsWith(pattern.replaceAll("\\*", ""))) {
                    return true;
                }
                if (pattern.startsWith("*") && uri.contains(pattern.replaceAll("\\*", ""))) {
                    return true;
                }
            } else {
                if (uri.equals(pattern)) {
                    return true;
                }
            }
        }

        return false;
    }
}
