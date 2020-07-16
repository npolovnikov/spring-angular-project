package ru.student.studentSpring.tutorial.service;

import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.InstrumentDaoImpl;
import ru.student.studentSpring.tutorial.dao.InstrumentListDao;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class InstrumentService extends BaseService<InstrumentListDto, InstrumentDto, InstrumentParams, Instruments> {

    private InstrumentDaoImpl instrumentDao;
    private MappingService mappingService;

    public InstrumentService(InstrumentListDao instrumentListDao, InstrumentDaoImpl instrumentDao,
                             MappingService mappingService) {
        super(mappingService, instrumentListDao, InstrumentListDto.class,
                instrumentDao, InstrumentDto.class, Instruments.class);
    }


    public List<InstrumentHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(instrumentDao.getHistory(idd), InstrumentHistoryDto.class);
    }

}
