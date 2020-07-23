package ru.dfsystems.spring.tutorial.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityFilterConfig {

    @Bean
    public FilterRegistrationBean<SecurityFilter> filterRegistrationBean(SecurityFilter filter) {
        var registrationBean = new FilterRegistrationBean<SecurityFilter>();
        registrationBean.setName("SecurityFilter");
        registrationBean.setFilter(filter);

        registrationBean.addInitParameter("public", "/*");

//TODO Убрать при релизе
//        registrationBean.addInitParameter("public", "/api/auth/login");
//        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
