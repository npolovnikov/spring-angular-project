package ru.dfsystems.spring.tutorial.service;


import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.listDao.LessonListDao;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {

    public LessonService(LessonListDao lessonListDao, LessonDaoImpl lessonDao, MappingService mappingService) {
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
    }
}