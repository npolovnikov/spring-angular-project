package ru.dfsystems.spring.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.service.CourseService;
import ru.dfsystems.spring.tutorial.service.QueueService;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
public class CourseController extends BaseController<CourseListDto, CourseDto, CourseParams, Course> {
    private CourseService courseService;
    private QueueService queueService;

    @Autowired
    public CourseController(CourseService courseService, QueueService queueService) {
        super(courseService);
        this.courseService = courseService;
        this.queueService = queueService;
    }

    /**
     * Возвращает список уроков
     */
    @PostMapping("/{idd}/lesson/list")
    public Page<LessonListDto> getLessonsByCourseIdd(@PathVariable("idd") Integer idd) {
        return courseService.getLessonsByCourseIdd(idd);
    }

    @Override
    public void create(@RequestBody CourseDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        /* мапим дто в строку */
        queueService.addToQueue(ObjectType.COURSE, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(@PathVariable("idd") Integer idd, @RequestBody CourseDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.COURSE, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void delete(@PathVariable("idd") Integer idd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CourseDto dto = new CourseDto();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.COURSE, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}
