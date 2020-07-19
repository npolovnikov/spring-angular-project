package ru.dfsystems.spring.tutorial.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.listDao.LessonListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class LessonService /*extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson>*/ {
    private MappingService mappingService;
    private LessonListDao lessonListDao;
    private LessonDaoImpl lessonDao;

/*    @Autowired
    public LessonService(LessonDaoImpl lessonDao, LessonListDao lessonListDao, MappingService mappingService){
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
        this.mappingService = mappingService;
        this.lessonDao = lessonDao;
        this.lessonListDao = lessonListDao;
    }*/
}