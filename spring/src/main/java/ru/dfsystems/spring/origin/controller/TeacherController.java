package ru.dfsystems.spring.origin.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.origin.dto.BaseListDto;
import ru.dfsystems.spring.origin.dto.Course.CourseListDto;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherHistoryDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherParams;
import ru.dfsystems.spring.origin.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.origin.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class TeacherController {
    private TeacherService teacherService;

    @PostMapping("/list")
    public Page<TeacherListDto> getList(@RequestBody PageParams<TeacherParams> pageParams) {
        return teacherService.getTeachersByParams(pageParams);
    }

    @PostMapping
    public void create(@RequestBody TeacherDto teacherDto){
        teacherService.create(teacherDto);
    }

    @GetMapping("/{idd}")
    public TeacherDto get(@PathVariable("idd") Integer idd){
        return teacherService.get(idd);
    }

    @PatchMapping("/{idd}")
    public TeacherDto update(@PathVariable("idd") Integer idd, @RequestBody TeacherDto teacherDto){
        return teacherService.update(idd, teacherDto);
    }

    @GetMapping("/{idd}/history")
    public List<TeacherHistoryDto> getHistory(@PathVariable("idd") Integer idd){
        return teacherService.getHistory(idd);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd){
        teacherService.delete(idd);
    }

    @GetMapping("/{idd}/course/list")
    public List<CourseListDto> getCourse(@PathVariable("idd") Integer idd){
        return teacherService.getCourse(idd);
    }
}