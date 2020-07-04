package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.service.CourseService;
import ru.student.studentSpring.tutorial.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/course", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class CourseController {
    private CourseService courseService;
    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<CoursesParams> pageParams) {
        Page<Courses> page = courseService.getCoursesByParam(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }


    private List<BaseListDto> mapper(List<Courses> allCourses) {
        return allCourses
                .stream().map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
