package ru.dfsystems.spring.tutorial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.TeacherService;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@RestController
@Tag(name = "Преподаватель", description = "Api Преподаватель")
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
public class TeacherController extends BaseController<TeacherListDto, TeacherDto, TeacherParams, Teacher> {

    private TeacherService teacherService;

    public TeacherController(BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> service,
                             TeacherService teacherService) {
        super(service);
        this.teacherService = teacherService;
    }

    @PostMapping("/list")
    @Operation(summary = "Список преподавателей", description = "", tags = {"teacher"})
    public Page<TeacherListDto> getList(@RequestBody PageParams<TeacherParams> params) {
        return teacherService.list(params);
    }

    @PostMapping
    @Operation(summary = "Добавить преподавателя", description = "", tags = {"teacher"})
    public void create(@RequestBody TeacherDto teacherDto) {
        teacherService.create(teacherDto);
    }

    @GetMapping("/{idd}")
    @Operation(summary = "Информация о преподавателе", description = "", tags = {"teacher"})
    public TeacherDto get(@PathVariable("idd") Integer idd) {
        return teacherService.get(idd);
    }

    @PatchMapping("/{idd}")
    @Operation(summary = "Обновить информацию о преподавателе", description = "", tags = {"teacher"})
    public TeacherDto update(@PathVariable("idd") Integer idd, @RequestBody TeacherDto teacherDto) {
        return teacherService.update(idd, teacherDto);
    }

    @DeleteMapping("/{idd}")
    @Operation(summary = "Удалить преподавателя", description = "", tags = {"teacher"})
    public void delete(@PathVariable("idd") Integer idd) {
        teacherService.delete(idd);
    }

}
