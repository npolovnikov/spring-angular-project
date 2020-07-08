package ru.dfsystems.spring.origin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.origin.dto.Course.CourseListDto;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.student.StudentDto;
import ru.dfsystems.spring.origin.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.origin.dto.student.StudentListDto;
import ru.dfsystems.spring.origin.dto.student.StudentParams;
import ru.dfsystems.spring.origin.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @PostMapping("/list")
    public Page<StudentListDto> getList(@RequestBody PageParams<StudentParams> pageParams){
        return studentService.getStudentByParams(pageParams);
    }

    @PostMapping
    public void create(@RequestBody StudentDto studentDto){
        studentService.create(studentDto);
    }

    @GetMapping("/{idd}")
    public StudentDto get(@PathVariable("idd") Integer idd){
        return studentService.get(idd);
    }
    @GetMapping("/{idd}/course/list")
    public List<CourseListDto> getCourse(@PathVariable("idd") Integer idd){
        return studentService.getCourse(idd);
    }

    @PatchMapping("/{idd}")
    public StudentDto update(@PathVariable("idd") Integer idd, @RequestBody StudentDto studentDto){
        return studentService.update(idd, studentDto);
    }

    @GetMapping("/{idd}/history")
    public List<StudentHistoryDto> getHistory(@PathVariable("idd") Integer idd){
        return studentService.getHistory(idd);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd){
        studentService.delete(idd);
    }

    @PutMapping("/{idd}/{idd}")
    public void putCourse(@PathVariable("idd")Integer idd, @PathVariable("idd") Integer courseIdd){
        studentService.putCourse(idd, courseIdd);
    }

}
