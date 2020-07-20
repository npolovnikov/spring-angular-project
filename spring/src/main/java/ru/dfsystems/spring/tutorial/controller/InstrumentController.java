package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.service.InstrumentService;
import ru.dfsystems.spring.tutorial.service.QueueService;

@RestController
@RequestMapping(value = "/instrument", produces = "application/json; charset=UTF-8")
@Api(tags =  {"Оборудование"}, description = "API для оборудования")
public class InstrumentController extends BaseController<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument> {
    private QueueService queueService;

    @Autowired
    public InstrumentController(InstrumentService instrumentService, QueueService queueService) {
        super(instrumentService);
        this.queueService = queueService;
    }

    @Override
    public void create(InstrumentDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        queueService.addToQueue(ObjectType.INSTRUMENT, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(Integer idd, InstrumentDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        queueService.addToQueue(ObjectType.INSTRUMENT, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void delete(Integer idd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InstrumentDto dto = new InstrumentDto();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.INSTRUMENT, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}
