package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.LessonListDao;
import ru.dfsystems.spring.tutorial.dao.standard.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {

    private LessonDaoImpl lessonDao;

    public LessonService(LessonListDao lessonListDao, LessonDaoImpl lessonDao, MappingService mappingService) {
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
        this.lessonDao = lessonDao;
    }

    @Transactional
    public Room getRoomFromLessonByIdd(Integer idd) {
        return lessonDao.getRoomFromLessonByIdd(idd);
    }
}