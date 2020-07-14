package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CourseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseHistoryDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.service.CourseService;

import java.util.List;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class CourseController {


    private final CourseService courseService;

    @PostMapping("/list")
    public Page<CourseListDto> getList(@RequestBody PageParams<CoursesParams> pageParams) {

        return courseService.getCourseByParam(pageParams);
    }

    @PostMapping
    public void create(@RequestBody CourseDto courseDto) {
        courseService.create(courseDto);
    }

    @GetMapping("/{idd}")
    public CourseDto get(@PathVariable(name = "idd") Integer idd) {

        return courseService.get(idd);
    }

    @PatchMapping("/{idd}")
    public CourseDto update(@PathVariable(name = "idd") Integer idd, @RequestBody CourseDto courseDto) {

        return courseService.update(idd, courseDto);
    }

    @GetMapping("/{idd}/history")
    public List<CourseHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return courseService.getHistory(idd);
    }
    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        courseService.delete(idd);
    }

}
