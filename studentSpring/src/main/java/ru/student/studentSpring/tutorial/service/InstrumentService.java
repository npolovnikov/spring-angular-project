package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.InstrumentDaoImpl;
import ru.student.studentSpring.tutorial.dao.InstrumentListDao;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentService {

    private final InstrumentListDao instrumentListDao;
    private final InstrumentDaoImpl instrumentDao;
    private final MappingService mappingService;

    public Page<InstrumentListDto> getInstrumentsByParam(PageParams<InstrumentParams> pageParams) {
        Page<Instruments> page = instrumentListDao.list(pageParams);
        List<InstrumentListDto> list = mappingService.mapList(page.getList(), InstrumentListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(InstrumentDto instrumentDto) {
        instrumentDao.create(mappingService.map(instrumentDto, Instruments.class));
    }

    public InstrumentDto get(Integer idd) {
        InstrumentDto dto = mappingService.map(instrumentDao.getActiveByIdd(idd), InstrumentDto.class);
        dto.setHistory(getHistory(idd));
        return dto;
    }

    public List<InstrumentHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(instrumentDao.getHistory(idd), InstrumentHistoryDto.class);
    }

    @Transactional
    public void delete(Integer idd) {
        Instruments instrument = instrumentDao.getActiveByIdd(idd);
        instrument.setDeleteDate(LocalDateTime.now());
        instrumentDao.update(instrument);
    }


    @Transactional
    public InstrumentDto update(Integer idd, InstrumentDto instrumentDto) {
        Instruments instrument = instrumentDao.getActiveByIdd(idd);
        if (instrument == null) {
            throw new RuntimeException("");
        }
        instrument.setDeleteDate(LocalDateTime.now());
        instrumentDao.update(instrument);
        Instruments newInstrument = mappingService.map(instrumentDto, Instruments.class);
        newInstrument.setIdd(instrument.getIdd());
        instrumentDao.create(newInstrument);
        return mappingService.map(instrument, InstrumentDto.class);
    }
}
