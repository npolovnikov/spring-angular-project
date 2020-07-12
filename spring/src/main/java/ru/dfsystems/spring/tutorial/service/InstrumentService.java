package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class InstrumentService  extends BaseService<InstrumentDto, InstrumentDto, InstrumentParams, Instrument>{

    @Autowired
    public InstrumentService(InstrumentListDao instrumentListDao, InstrumentDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, InstrumentDto.class, InstrumentDto.class, Instrument.class);
    }
}
