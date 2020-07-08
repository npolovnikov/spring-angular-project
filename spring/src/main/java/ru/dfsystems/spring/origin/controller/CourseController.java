package ru.dfsystems.spring.origin.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.origin.dto.BaseListDto;
import ru.dfsystems.spring.origin.dto.Course.CourseDto;
import ru.dfsystems.spring.origin.dto.Course.CourseListDto;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.Course.CourseParams;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.generated.tables.pojos.Course;
import ru.dfsystems.spring.origin.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class CourseController {
    private CourseService courseService;

    @PostMapping("/list")
    public Page<CourseListDto> getList(@RequestBody PageParams<CourseParams> pageParams) {
        return courseService.getCoursesByParams(pageParams);
    }

    @PostMapping
    public void create(@RequestBody CourseDto courseDto){
        courseService.create(courseDto);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd){
        courseService.delete(idd);
    }

    @GetMapping("/{idd}")
    public CourseDto get(@PathVariable("idd") Integer idd){
        return courseService.get(idd);
    }

    @PatchMapping("/{idd}")
    public CourseDto update(@PathVariable("idd") Integer idd, @RequestBody CourseDto courseDto){
        return courseService.update(idd, courseDto);
    }

    private List<BaseListDto> mapper(List<Course> allCourses) {
        ModelMapper mapper = new ModelMapper();
        return allCourses.stream()
                .map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
