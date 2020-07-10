package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.RoomService;
import ru.dfsystems.spring.tutorial.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Student>{
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }

    @PutMapping("/{idd}/{idd}")
    public void putCourse(@PathVariable("idd")Integer idd, @PathVariable("idd") Integer courseIdd){
        studentService.putCourse(idd, courseIdd);
    }

}
