package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseList;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.service.CourseService;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
@Api(tags =  {"Курсы"}, description = "API для курсов")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/list")
    public Page<CourseList> getList(@RequestBody PageParams<CourseParams> pageParams) {
        return courseService.list(pageParams);
    }

    @PostMapping
    public void create(@RequestBody CourseDto dto) {
        courseService.create(dto, null);
    }

    @GetMapping("/{idd}")
    public CourseDto get(@PathVariable("idd") Integer idd) {
        return courseService.get(idd);
    }

    @PatchMapping("/{idd}")
    public CourseDto update(@PathVariable("idd") Integer idd, @RequestBody CourseDto dto) {
        return courseService.update(idd, dto, null);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd) {
        courseService.delete(idd, null);
    }
}
