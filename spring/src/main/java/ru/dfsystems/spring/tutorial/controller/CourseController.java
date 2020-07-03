package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.service.CourseService;
import ru.dfsystems.spring.tutorial.service.CourseService;
import ru.dfsystems.spring.tutorial.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")

@Api(value = "/course", description = "Оперции с курсами")
public class CourseController extends BaseController<CourseListDto, CourseDto, CourseParams, Course> {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }
    
    @PutMapping("/{idd}/lesson")
    @ApiOperation(value = "Присваивает урок курсу")
    public void putLesson(@PathVariable("idd") Integer idd, @RequestBody Integer lessonIdd) {
        courseService.putLesson(idd, lessonIdd);
    }

    @PutMapping("/{idd}/student")
    @ApiOperation(value = "Присваивает студента курсу")
    public void putStudent(@PathVariable("idd") Integer idd, @RequestBody Integer lessonIdd) {
        courseService.putStudent(idd, lessonIdd);
    }

    @PutMapping("/{idd}/teacher")
    @ApiOperation(value = "Присваивает преподавателя курсу")
    public void putTeacher(@PathVariable("idd") Integer idd, @RequestBody Integer teacherIdd) {
        courseService.putTeacher(idd, teacherIdd);
    }

}
