package ru.dfsystems.spring.tutorial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.service.InstrumentService;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@RestController
@Tag(name = "Иструмент", description = "Api Инструмент")
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class InstrumentController {

    private InstrumentService instrumentService;

    @PostMapping("/list")
    @Operation(summary = "Список инструментов", description = "", tags = {"instrument"})
    public Page<InstrumentListDto> getList(@RequestBody PageParams<InstrumentParams> params) {
        return instrumentService.getInstrumentsByParams(params);
    }

    @PostMapping
    @Operation(summary = "Добавить инструмент", description = "", tags = {"instrument"})
    public void create(@RequestBody InstrumentDto instrumentDto) {
        instrumentService.create(instrumentDto);
    }

    @GetMapping("/{idd}")
    @Operation(summary = "Информация об иснтрументе", description = "", tags = {"instrument"})
    public InstrumentDto get(@PathVariable("idd") Integer idd) {
        return instrumentService.get(idd);
    }

    @PatchMapping("/{idd}")
    @Operation(summary = "Обновить информацию об инструменте", description = "", tags = {"instrument"})
    public InstrumentDto update(@PathVariable("idd") Integer idd, @RequestBody InstrumentDto instrumentDto) {
        return instrumentService.update(idd, instrumentDto);
    }

    @DeleteMapping("/{idd}")
    @Operation(summary = "Удалить инструмент", description = "", tags = {"instrument"})
    public void delete(@PathVariable("idd") Integer idd) {
        instrumentService.delete(idd);
    }
}
