package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.service.InstrumentService;
import ru.dfsystems.spring.tutorial.service.LessonService;
import ru.dfsystems.spring.tutorial.service.LessonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lesson", produces = "application/json; charset=UTF-8")
@Api(value = "/lesson", description = "Оперции с уроками")
public class LessonController extends BaseController<LessonListDto, LessonDto, LessonParams, Lesson>{
    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        super(lessonService);
        this.lessonService = lessonService;
    }

    @PutMapping("/{idd}/instrument")
    @ApiOperation(value = "Присваивает инструмент уроку")
    public void putLesson(@PathVariable("idd") Integer idd, @RequestBody Integer instrumentIdd) {
        lessonService.putInstrument(idd, instrumentIdd);
    }

    @PutMapping("/{idd}/course")
    @ApiOperation(value = "Присваивает курс уроку")
    public void putCourse(@PathVariable("idd") Integer idd, @RequestBody Integer courseIdd) {
        lessonService.putCourse(idd, courseIdd);
    }

    @PutMapping("/{idd}/room")
    @ApiOperation(value = "Присваивает комнату уроку")
    public void putRoom(@PathVariable("idd") Integer idd, @RequestBody Integer roomIdd) {
        lessonService.putRoom(idd, roomIdd);
    }


}
