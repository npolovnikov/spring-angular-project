package ru.student.studentSpring.tutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import ru.student.studentSpring.tutorial.service.InstrumentService;

import java.util.List;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
public class InstrumentController extends BaseController<InstrumentListDto,
        InstrumentDto, InstrumentParams, Instruments> {
    private InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        super(instrumentService);
        this.instrumentService = instrumentService;
    }

    @GetMapping("/{idd}/history")
    public List<InstrumentHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return instrumentService.getHistory(idd);
    }
}
