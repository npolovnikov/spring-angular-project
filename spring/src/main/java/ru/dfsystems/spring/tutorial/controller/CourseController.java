package ru.dfsystems.spring.tutorial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.CourseService;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@RestController
@Tag(name = "Курс", description = "Api Курс")
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
public class CourseController extends BaseController<CourseListDto, CourseDto, CourseParams, Course> {

    private CourseService courseService;

    public CourseController(BaseService<CourseListDto, CourseDto, CourseParams, Course> service,
                            CourseService courseService) {
        super(service);
        this.courseService = courseService;
    }

    @PostMapping("/list")
    @Operation(summary = "Список курсов", description = "", tags = {"course"})
    public Page<CourseListDto> getList(@RequestBody PageParams<CourseParams> params) {
        return courseService.list(params);
    }

    @PostMapping
    @Operation(summary = "Добавить курс", description = "", tags = {"course"})
    public void create(@RequestBody CourseDto courseDto) {
        courseService.create(courseDto);
    }

    @GetMapping("/{idd}")
    @Operation(summary = "Информация о курсе", description = "", tags = {"course"})
    public CourseDto get(@PathVariable("idd") Integer idd) {
        return courseService.get(idd);
    }

    @PatchMapping("/{idd}")
    @Operation(summary = "Обновить информацию о курсе", description = "", tags = {"course"})
    public CourseDto update(@PathVariable("idd") Integer idd, @RequestBody CourseDto courseDto) {
        return courseService.update(idd, courseDto);
    }

    @DeleteMapping("/{idd}")
    @Operation(summary = "Удалить курс", description = "", tags = {"course"})
    public void delete(@PathVariable("idd") Integer idd) {
        courseService.delete(idd);
    }

}
