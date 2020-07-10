package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.service.CourseService;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
public class CourseController extends BaseController<CourseListDto, CourseDto, CourseParams, Course> {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }
}
