package ru.dfsystems.spring.tutorial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.StudentService;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@RestController
@Tag(name = "Студент", description = "Api Студент")
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Student> {

    private StudentService studentService;

    @Autowired
    public StudentController(BaseService<StudentListDto, StudentDto, StudentParams, Student> service,
                             StudentService studentService) {
        super(service);
        this.studentService = studentService;
    }

    @PostMapping("/list")
    @Operation(summary = "Список студентов", description = "", tags = {"student"})
    public Page<StudentListDto> getList(@RequestBody PageParams<StudentParams> params) {
        return studentService.list(params);
    }

    @PostMapping
    @Operation(summary = "Добавить студента", description = "", tags = {"student"})
    public void create(@RequestBody StudentDto studentDto) {
        studentService.create(studentDto);
    }

    @GetMapping("/{idd}")
    @Operation(summary = "Инфорбация о студенте", description = "", tags = {"student"})
    public StudentDto get(@PathVariable("idd") Integer idd) {
        return studentService.get(idd);
    }

    @PatchMapping("/{idd}")
    @Operation(summary = "Обновить информацию о студенте", description = "", tags = {"student"})
    public StudentDto update(@PathVariable("idd") Integer idd, @RequestBody StudentDto studentDto) {
        return studentService.update(idd, studentDto);
    }

    @DeleteMapping("/{idd}")
    @Operation(summary = "Удалить студента", description = "", tags = {"student"})
    public void delete(@PathVariable("idd") Integer idd) {
        studentService.delete(idd);
    }

}
