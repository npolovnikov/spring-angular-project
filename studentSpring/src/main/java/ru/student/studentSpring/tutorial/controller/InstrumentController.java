package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import ru.student.studentSpring.tutorial.service.InstrumentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class InstrumentController {
    private InstrumentService instrumentService;
    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<InstrumentParams> pageParams) {
        Page<Instruments> page = instrumentService.getInstrumentsByParam(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }


    private List<BaseListDto> mapper(List<Instruments> allInstruments) {
        return allInstruments
                .stream().map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
