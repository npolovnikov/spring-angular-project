package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dao.standard.InstrumentDaoImpl;
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

    private InstrumentListDao instrumentListDao;
    private InstrumentDaoImpl instrumentDao;
    private MappingService mappingService;

    @Transactional
    public List<Instrument> getAllInstruments() {
        instrumentDao.fetchOneById(152);
        instrumentDao.getActiveByIdd(152);
        return instrumentDao.findAll();
    }

    @Transactional
    public Page<InstrumentListDto> getList(PageParams<InstrumentParams> pageParams) {
        Page<Instrument> page = instrumentListDao.getSortedList(pageParams);
        List<InstrumentListDto> list = mappingService.mapList(page.getList(), InstrumentListDto.class);

        return new Page<>(list, page.getTotalCount());
    }
}