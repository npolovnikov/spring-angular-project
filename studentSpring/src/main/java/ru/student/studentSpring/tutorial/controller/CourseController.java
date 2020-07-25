package ru.student.studentSpring.tutorial.controller;

import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.course.CourseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseHistoryDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;
import ru.student.studentSpring.tutorial.service.CourseService;

import java.util.List;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
public class CourseController  extends BaseController<CourseListDto, CourseDto, CoursesParams, Courses> {


    private CourseService courseService;

    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }

    @GetMapping("/{idd}/history")
    public List<CourseHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return courseService.getHistory(idd);
    }

//    @PutMapping("/{idd}/teacher")
//    public void putTeacher(@PathVariable(name = "idd") Integer idd, @RequestBody Integer instrumentId) {
//        courseService.(idd, instrumentId);
//    }

}
