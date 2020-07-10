package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonListDao;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {

    @Autowired
    public LessonService(LessonListDao instrumentListDao, LessonDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, LessonListDto.class, LessonDto.class, Lesson.class);
    }
}
