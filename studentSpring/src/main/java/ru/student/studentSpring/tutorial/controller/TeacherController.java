package ru.student.studentSpring.tutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
public class TeacherController extends BaseController<TeacherListDto,
        TeacherDto, TeacherParams, Teachers> {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        super(teacherService);
        this.teacherService = teacherService;
    }

    @GetMapping("/{idd}/history")
    public List<TeacherHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return teacherService.getHistory(idd);
    }

}
