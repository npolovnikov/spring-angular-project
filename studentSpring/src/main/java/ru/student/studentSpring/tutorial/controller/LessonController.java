package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CourseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseHistoryDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.dto.lesson.LessonDto;
import ru.student.studentSpring.tutorial.dto.lesson.LessonListDto;
import ru.student.studentSpring.tutorial.dto.lesson.LessonParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Lessons;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.service.CourseService;
import ru.student.studentSpring.tutorial.service.LessonService;
import ru.student.studentSpring.tutorial.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/list")
    public Page<LessonListDto> getList(@RequestBody PageParams<LessonParams> pageParams) {

        return lessonService.getLessonsByParam(pageParams);
    }

    @PostMapping
    public void create(@RequestBody LessonDto lessonDto) {
        lessonService.create(lessonDto);
    }

    @GetMapping("/{idd}")
    public LessonDto get(@PathVariable(name = "idd") Integer idd) {

        return lessonService.get(idd);
    }

    @PatchMapping("/{idd}")
    public LessonDto update(@PathVariable(name = "idd") Integer idd, @RequestBody LessonDto lessonDto) {

        return lessonService.update(idd, lessonDto);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        lessonService.delete(idd);
    }

}
