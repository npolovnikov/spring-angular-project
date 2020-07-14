package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.service.InstrumentService;

import java.util.List;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class InstrumentController {
    private final InstrumentService instrumentService;

    @PostMapping("/list")
    public Page<InstrumentListDto> getList(@RequestBody PageParams<InstrumentParams> pageParams) {

        return instrumentService.getInstrumentsByParam(pageParams);
    }

    @PostMapping
    public void create(@RequestBody InstrumentDto instrumentDto) {
        instrumentService.create(instrumentDto);
    }

    @GetMapping("/{idd}")
    public InstrumentDto get(@PathVariable(name = "idd") Integer idd) {

        return instrumentService.get(idd);
    }

    @PatchMapping("/{idd}")
    public InstrumentDto update(@PathVariable(name = "idd") Integer idd, @RequestBody InstrumentDto instrumentDto) {

        return instrumentService.update(idd, instrumentDto);
    }

    @GetMapping("/{idd}/history")
    public List<InstrumentHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return instrumentService.getHistory(idd);
    }
    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        instrumentService.delete(idd);
    }

}
