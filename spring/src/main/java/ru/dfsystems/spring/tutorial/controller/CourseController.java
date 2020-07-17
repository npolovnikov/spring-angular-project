package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
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

    /**
     * Возвращает список уроков
     */
    @PostMapping("/{idd}/lesson/list")
    public Page<LessonListDto> getLessonsByCourseIdd(@PathVariable("idd") Integer idd) {
        return courseService.getLessonsByCourseIdd(idd);
    }
}
