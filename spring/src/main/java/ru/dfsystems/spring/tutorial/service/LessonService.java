package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;

@Service
@AllArgsConstructor
public class LessonService {
    private LessonDaoImpl lessonDao;
}
