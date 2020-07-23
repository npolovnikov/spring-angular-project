package ru.dfsystems.spring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonHistoryDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.service.LessonService;

import java.util.List;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
public class LessonController extends BaseController<LessonListDto, LessonDto, LessonParams, Lesson> {
    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService service) {
        super(service);
        this.lessonService = service;
    }

    @GetMapping("/{idd}/instrument/list")
    public List<InstrumentListDto> getInstrumentList(@PathVariable("idd") Integer idd) {
        return lessonService.getInstruments(idd);
    }

    @PutMapping("/{idd}/instrument")
    public void putInstrument(@PathVariable("idd") Integer idd,
                              @RequestBody Integer instrumentIdd) {
        lessonService.putInstrument(idd, instrumentIdd);
    }
}
