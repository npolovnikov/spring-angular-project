package ru.dfsystems.spring.tutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    /* Когда прилетает  эксепшен класса Exception.class он перехватывается */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e) {
        e.printStackTrace();
        /*  и отправляется ответ в виде INTERNAL_SERVER_ERROR и мессадж иксепшена */
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}