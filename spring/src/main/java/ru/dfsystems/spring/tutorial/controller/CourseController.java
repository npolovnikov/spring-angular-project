package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.service.CourseService;

import java.util.List;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
public class CourseController extends BaseController<CourseListDto, CourseDto, CourseParams, Course> {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService service) {
        super(service);
        courseService = service;
    }

    @GetMapping("/{idd}/lessons")
    public List<Lesson> getLessonsByIdd(@PathVariable("idd") Integer idd) {
        return courseService.getLessonsByIdd(idd);
    }
}