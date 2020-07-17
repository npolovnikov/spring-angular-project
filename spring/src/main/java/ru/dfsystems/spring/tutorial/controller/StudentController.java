package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.StudentService;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Student> {
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }

    /**
     * Возвращает курсы, на которые записан студент
     */
    @PostMapping("/{idd}/course/list")
    public Page<CourseListDto> getCoursesByStudentIdd(@PathVariable("idd") Integer idd) {
        return studentService.getCoursesByStudentIdd(idd);
    }

    /**
     * Запись сутдента на новый курс
     */
    @PutMapping("/{idd}/course")
    public void putCourse(@PathVariable("idd") Integer idd, @RequestBody Integer courseIdd) {
        studentService.putCourse(idd, courseIdd);
    }
}
