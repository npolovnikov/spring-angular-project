package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.StudentService;

import java.util.List;

@ApiOperation(value = "Сервис для работы с профилями учеников")
@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
                        @ApiResponse(code = 201, message = "Create")})
@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Student> {

    private StudentService studentService;

    public StudentController(StudentService service) {
        super(service);
        this.studentService = service;
    }

    @GetMapping("/{idd}/course/list")
    public List<CourseListDto> getCourseList(@PathVariable("idd") Integer idd) {
        return studentService.getCourses(idd);
    }

    @PutMapping("/{idd}/course")
    public void addInCourse(@PathVariable("idd") Integer idd,
                               @RequestBody Integer courseIdd) {
        studentService.addInCourse(idd, courseIdd);
    }
}
