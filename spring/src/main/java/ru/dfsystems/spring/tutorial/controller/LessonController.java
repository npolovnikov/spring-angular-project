package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.LessonService;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
public class LessonController extends BaseController<LessonListDto, LessonDto, LessonParams, Lesson> {
    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService service) {
        super(service);
    }

//    @PostMapping("/list")
//    public Page<LessonListDto> getList(@RequestBody PageParams<LessonParams> pageParams) {
//        return lessonService.getLessonsByParams(pageParams);
//    }
//
//    @PostMapping
//    public void create(@RequestBody LessonListDto lessonDto) {
//        lessonService.create(lessonDto);
//    }
//
//    @GetMapping("/{id}")
//    public LessonDto get(@PathVariable("id") Integer id) {
//        return lessonService.get(id);
//    }
//
//    @PatchMapping("/{id}")
//    public LessonDto update(@PathVariable("id") Integer id, @RequestBody LessonDto lessonDto) {
//        return lessonService.update(id, lessonDto);
//    }
//
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable("id") Integer id) {
//        lessonService.delete(id);
//    }

    @PutMapping("/{id}/instrument")
    public void putInstrument(@PathVariable("id") Integer id, @RequestBody Integer instrumentIdd) {
        lessonService.putInstrument(id,  instrumentIdd);
    }
}
