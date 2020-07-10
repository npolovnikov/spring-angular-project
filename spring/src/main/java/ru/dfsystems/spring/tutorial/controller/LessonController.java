package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.service.LessonService;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
public class LessonController extends BaseController<LessonListDto, LessonDto, LessonParams, Lesson> {

    private LessonService courseService;

    @Autowired
    public LessonController(LessonService courseService) {
        super(courseService);
        this.courseService = courseService;
    }
}
