package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;

import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentService {

    private InstrumentDaoImpl instrumentDao;

    public List<Instrument> getAllInstruments() {
        return instrumentDao.findAll();
    }

    public Page<Instrument> getInstrumentsByParams(PageParams<InstrumentParams> pageParams) {
        return instrumentDao.getInstrumentsByParams( pageParams);
    }
}
