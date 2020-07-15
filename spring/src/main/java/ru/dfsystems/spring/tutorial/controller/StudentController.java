package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.StudentService;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Student> {
    @Autowired
    public StudentController(StudentService studentService) {
        super(studentService);
    }
}
