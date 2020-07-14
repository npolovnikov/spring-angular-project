package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.service.InstrumentService;
import ru.student.studentSpring.tutorial.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/list")
    public Page<TeacherListDto> getList(@RequestBody PageParams<TeacherParams> pageParams) {

        return teacherService.getTeachersByParam(pageParams);
    }

    @PostMapping
    public void create(@RequestBody TeacherDto teacherDto) {
        teacherService.create(teacherDto);
    }

    @GetMapping("/{idd}")
    public TeacherDto get(@PathVariable(name = "idd") Integer idd) {

        return teacherService.get(idd);
    }

    @PatchMapping("/{idd}")
    public TeacherDto update(@PathVariable(name = "idd") Integer idd, @RequestBody TeacherDto teacherDto) {

        return teacherService.update(idd, teacherDto);
    }

    @GetMapping("/{idd}/history")
    public List<TeacherHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return teacherService.getHistory(idd);
    }
    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        teacherService.delete(idd);
    }

}
