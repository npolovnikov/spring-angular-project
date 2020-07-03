package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.CourseMapper;
import ru.dfsystems.spring.tutorial.mapping.InstrumentMapper;
import ru.dfsystems.spring.tutorial.mapping.TeacherMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
@AllArgsConstructor
public class InstrumentService {

    private InstrumentDaoImpl instrumentDao;
    private InstrumentListDao instrumentListDao;

    public Page<InstrumentListDto> getInstrumentsByParams(PageParams<InstrumentParams> pageParams) {
        Page<Instrument> page = instrumentListDao.list(pageParams);
        List<InstrumentListDto> list = InstrumentMapper.INSTRUMENT_MAPPER.instrumentListToInstrumentListDto(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(InstrumentDto instrumentDto) {
        instrumentDao.create(InstrumentMapper.INSTRUMENT_MAPPER.instrumentDtoToInstrument(instrumentDto));
    }

    public InstrumentDto get(Integer idd) {
        return InstrumentMapper.INSTRUMENT_MAPPER.instrumentToInstrumentDto(instrumentDao.getByIdd(idd));
    }

    @Transactional
    public void delete(Integer idd) {
        Instrument instrument = instrumentDao.getByIdd(idd);
        instrument.setDeleteDate(LocalDateTime.now());
        instrumentDao.update(instrument);
    }

    @Transactional
    public InstrumentDto update(Integer idd, InstrumentDto instrumentDto) {
        Instrument instrument = instrumentDao.getByIdd(idd);
        if (instrument == null){
            throw new RuntimeException("");
        }
        instrument.setDeleteDate(LocalDateTime.now());
        instrumentDao.update(instrument);

        Instrument newInstrument = InstrumentMapper.INSTRUMENT_MAPPER.instrumentDtoToInstrument(instrumentDto);
        newInstrument.setIdd(instrument.getIdd());
        instrumentDao.create(newInstrument);
        return InstrumentMapper.INSTRUMENT_MAPPER.instrumentToInstrumentDto(newInstrument);
    }

}
