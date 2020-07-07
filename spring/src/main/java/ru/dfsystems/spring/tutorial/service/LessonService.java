package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

@Service
@AllArgsConstructor
public class LessonService {
    private LessonDaoImpl lessonDao;

    public Page<Lesson> getLessonsByParams(PageParams<LessonParams> pageParams) {
        return lessonDao.getLessonsByParams(pageParams);
    }
}
