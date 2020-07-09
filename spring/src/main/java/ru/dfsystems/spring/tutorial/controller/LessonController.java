package ru.dfsystems.spring.tutorial.controller;

public class LessonController {

    /**
@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
public class LessonController extends BaseController<LessonListDto, LessonDto, LessonParams, Lesson>{
private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        super(lessonService);
        this.lessonService = lessonService;
    }
}






     */



}
