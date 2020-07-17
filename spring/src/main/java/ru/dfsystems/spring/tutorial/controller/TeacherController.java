package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.service.TeacherService;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
public class TeacherController extends BaseController<TeacherListDto, TeacherDto, TeacherParams, Teacher> {
    TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        super(teacherService);
        this.teacherService = teacherService;
    }

    /**
     * Возвращает список курсов учителя
     */
    @PostMapping("/{idd}/course/list")
    public Page<CourseListDto> getCoursesByTeacherIdd(@PathVariable("idd") Integer idd) {
        return teacherService.getCoursesByTeacherIdd(idd);
    }
}
