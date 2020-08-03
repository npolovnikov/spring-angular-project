package ru.dfsystems.spring.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.service.LessonService;
import ru.dfsystems.spring.tutorial.service.QueueService;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
public class LessonController extends BaseController<LessonListDto, LessonDto, LessonParams, Lesson> {
    private LessonService lessonService;
    private QueueService queueService;

    @Autowired
    public LessonController(LessonService lessonService, QueueService queueService) {
        super(lessonService);
        this.lessonService = lessonService;
        this.queueService = queueService;
    }

    @Override
    public void create(@RequestBody LessonDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        /* мапим дто в строку */
        queueService.addToQueue(ObjectType.LESSON, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(@PathVariable("idd") Integer idd, @RequestBody LessonDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.LESSON, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void delete(@PathVariable("idd") Integer idd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LessonDto dto = new LessonDto();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.LESSON, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}