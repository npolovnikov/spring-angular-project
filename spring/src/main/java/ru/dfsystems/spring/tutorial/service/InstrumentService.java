package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.listDao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InstrumentService extends BaseService<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument>{
    private InstrumentDaoImpl instrumentDao;
    private InstrumentListDao instrumentListDao;
    private MappingService mappingService;

    public InstrumentService(InstrumentDaoImpl instrumentDao, InstrumentListDao instrumentListDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, InstrumentListDto.class, InstrumentDto.class, Instrument.class);
        this.instrumentDao = instrumentDao;
        this.instrumentListDao = instrumentListDao;
        this.mappingService = mappingService;
    }
}
