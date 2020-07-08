package ru.dfsystems.spring.origin.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.lesson.LessonDto;
import ru.dfsystems.spring.origin.dto.lesson.LessonListDto;
import ru.dfsystems.spring.origin.dto.lesson.LessonParams;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.service.LessonService;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class LessonController {
    private LessonService lessonService;

    @PostMapping("/list")
    public Page<LessonListDto> getList(PageParams<LessonParams> pageParams) {
        return lessonService.getLessonsByParams(pageParams);
    }
    @GetMapping("/{id}")
    public LessonDto get(@PathVariable("id") Integer id){
        return lessonService.get(id);
    }
}