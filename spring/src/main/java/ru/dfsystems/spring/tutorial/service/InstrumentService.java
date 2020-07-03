package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.ListDao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dao.ListDao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonToInstrumentsDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.*;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InstrumentService extends BaseService<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument>{
    private InstrumentToRoomDao instrumentToRoomDao;
    private LessonToInstrumentsDao lessonToInstrumentsDao;

    @Autowired
    public InstrumentService(InstrumentListDao instrumentListDao, BaseDao<Instrument> instrumentDao, MappingService mappingService
            , InstrumentToRoomDao studentToInstrumentDao, LessonToInstrumentsDao lessonToInstrumentDao) {
        super(mappingService, instrumentListDao, instrumentDao, InstrumentListDto.class, InstrumentDto.class, Instrument.class);
        this.instrumentToRoomDao = studentToInstrumentDao;
        this.lessonToInstrumentsDao = lessonToInstrumentDao;
    }

    @Transactional
    public void putRoom(Integer idd, Integer roomIdd) {
        InstrumentToRoom link = new InstrumentToRoom();
        link.setInstrumentIdd(idd);
        link.setInstrumentIdd(roomIdd);
        instrumentToRoomDao.insert(link);
    }

    @Transactional
    public void putLesson(Integer idd, Integer lessonIdd) {
        LessonToInstruments link = new LessonToInstruments();
        link.setInstrumentIdd(idd);
        link.setLessonIdd(lessonIdd);
        lessonToInstrumentsDao.insert(link);
    }





}
