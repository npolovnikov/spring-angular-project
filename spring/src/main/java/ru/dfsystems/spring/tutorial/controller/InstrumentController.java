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
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.service.CourseService;
import ru.dfsystems.spring.tutorial.service.InstrumentService;
import ru.dfsystems.spring.tutorial.service.InstrumentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
@Api(value = "/instrument", description = "Оперции с инструментами")
public class InstrumentController extends BaseController< InstrumentListDto,  InstrumentDto,  InstrumentParams,  Instrument>{
    private InstrumentService instrumentService;

    @Autowired
    public InstrumentController(InstrumentService instrumentService) {
        super(instrumentService);
        this.instrumentService = instrumentService;
    }

    @PutMapping("/{idd}/lesson")
    @ApiOperation(value = "Присваивает инструмент уроку")
    public void putLesson(@PathVariable("idd") Integer idd, @RequestBody Integer lessonIdd) {
        instrumentService.putLesson(idd, lessonIdd);
    }

    @PutMapping("/{idd}/room")
    @ApiOperation(value = "Присваивает инструмент комнате")
    public void putRoom(@PathVariable("idd") Integer idd, @RequestBody Integer roomIdd) {
        instrumentService.putRoom(idd, roomIdd);
    }

}
