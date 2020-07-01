package ru.dfsystems.spring.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.dfsystems.spring.tutorial.config.SwaggerConfig;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(new Class[] {
                Application.class,
                SwaggerConfig.class
        }, args);
    }
}
