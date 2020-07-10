package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.Course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.Course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.service.CourseService;
import ru.dfsystems.spring.tutorial.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
public class CourseController extends BaseController<CourseListDto, CourseDto, CourseParams, Course>{
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }
}
