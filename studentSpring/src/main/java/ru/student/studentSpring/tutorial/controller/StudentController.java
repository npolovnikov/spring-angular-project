package ru.student.studentSpring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.student.StudentDto;
import ru.student.studentSpring.tutorial.dto.student.StudentHistoryDto;
import ru.student.studentSpring.tutorial.dto.student.StudentListDto;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Students> {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }


    @GetMapping("/{idd}/history")
    public List<StudentHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return studentService.getHistory(idd);
    }

    @PutMapping("/{idd}/instrument")
    public void putCourse(@PathVariable(name = "idd") Integer idd, @RequestBody Integer instrumentId) {
        studentService.putCourse(idd, instrumentId);
    }
}
