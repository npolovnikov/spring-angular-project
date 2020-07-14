package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.standard.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.list.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class InstrumentService extends BaseService<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument> {

    private InstrumentDaoImpl instrumentDao;

    @Autowired
    public InstrumentService(InstrumentListDao instrumentListDao, InstrumentDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, InstrumentListDto.class, InstrumentDto.class, Instrument.class);
        this.instrumentDao = instrumentDao;
    }

    @Transactional
    public List<Instrument> getInstrumentsByRoomIdd(Integer idd){
        return instrumentDao.getInstrumentsByRoomIdd(idd);
    }
}

