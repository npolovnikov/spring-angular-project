package ru.dfsystems.spring.tutorial.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonListDao;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {
    private LessonListDao lessonListDao;
    private MappingService mappingService;

    @Autowired
    public LessonService(LessonListDao lessonListDao,
                         LessonDaoImpl lessonDao,
                         MappingService mappingService) {
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
        this.mappingService = mappingService;
        this.lessonListDao = lessonListDao;
    }

    @Override
    protected void doCreate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
       LessonDto dto = om.readValue(objectData, LessonDto.class);
        create(dto, userId);
    }

    @Override
    protected void doUpdate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        LessonDto dto = om.readValue(objectData, LessonDto.class);
        update(dto.getIdd(), dto, userId);
    }

    @Override
    protected void doDelete(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        LessonDto dto = om.readValue(objectData, LessonDto.class);
        delete(dto.getIdd(), userId);
    }

    public static void main(String[] args) {
        HashMap hashMap = new HashMap<>();
        ArrayList array;
    }
}
