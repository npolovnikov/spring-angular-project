package ru.dfsystems.spring.origin.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.origin.dto.BaseListDto;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.origin.service.InstrumentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class InstrumentController {
    private InstrumentService instrumentService;

    @PostMapping("/list")
    public Page<InstrumentListDto> getList(@RequestBody PageParams<InstrumentParams> pageParams){
        return instrumentService.getInstrumentByParams(pageParams);
    }

    @PostMapping
    public void create(@RequestBody InstrumentDto instrumentDto){
        instrumentService.create(instrumentDto);
    }

    @GetMapping("/{idd}")
    public InstrumentDto get(@PathVariable("idd") Integer idd){
        return instrumentService.get(idd);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd){
        instrumentService.delete(idd);
    }

}
