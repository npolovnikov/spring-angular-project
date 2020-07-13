package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.istrument.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.istrument.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentService {
    private InstrumentDaoImpl instrumentDao;
    private InstrumentListDao instrumentListDao;
    private MappingService mappingService;

    public Page<InstrumentListDto> getInstrumentsByParams(PageParams<InstrumentParams> pageParams) {
        Page<Instrument> page = instrumentListDao.list(pageParams);
        List<InstrumentListDto> list = mappingService.mapList(page.getList(), InstrumentListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
