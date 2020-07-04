package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.lesson.LessonParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Lessons;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.service.LessonService;
import ru.student.studentSpring.tutorial.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class LessonController {
    private LessonService lessonService;
    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<LessonParams> pageParams) {
        Page<Lessons> page = lessonService.getLessonsByParam(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }


    private List<BaseListDto> mapper(List<Lessons> allLessons) {
        return allLessons
                .stream().map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
