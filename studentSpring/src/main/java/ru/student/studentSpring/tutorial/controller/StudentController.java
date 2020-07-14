package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.dto.student.StudentDto;
import ru.student.studentSpring.tutorial.dto.student.StudentHistoryDto;
import ru.student.studentSpring.tutorial.dto.student.StudentListDto;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.service.RoomService;
import ru.student.studentSpring.tutorial.service.StudentService;
import ru.student.studentSpring.tutorial.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/list")
    public Page<StudentListDto> getList(@RequestBody PageParams<StudentParams> pageParams) {

        return studentService.getStudentsByParam(pageParams);
    }

    @PostMapping
    public void create(@RequestBody StudentDto studentDto) {
        studentService.create(studentDto);
    }

    @GetMapping("/{idd}")
    public StudentDto get(@PathVariable(name = "idd") Integer idd) {

        return studentService.get(idd);
    }

    @PatchMapping("/{idd}")
    public StudentDto update(@PathVariable(name = "idd") Integer idd, @RequestBody StudentDto studentDto) {

        return studentService.update(idd, studentDto);
    }

    @GetMapping("/{idd}/history")
    public List<StudentHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return studentService.getHistory(idd);
    }
    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        studentService.delete(idd);
    }

    @PutMapping("/{idd}/instrument")
    public void putCourse(@PathVariable(name = "idd") Integer idd, @RequestBody Integer instrumentId) {
        studentService.putCourse(idd, instrumentId);
    }
}
