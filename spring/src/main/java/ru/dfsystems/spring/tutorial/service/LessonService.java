package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonListDao;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToLessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToLesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {
    private final InstrumentDaoImpl instrumentDao;
    private final MappingService mappingService;
    private final InstrumentToLessonDao instrumentToLessonDao;

    @Autowired
    public LessonService(MappingService mappingService, LessonListDao lessonListDao,
                         LessonDaoImpl lessonDao, InstrumentDaoImpl instrumentDao,
                         InstrumentToLessonDao instrumentToLessonDao) {
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
        this.instrumentDao = instrumentDao;
        this.mappingService = mappingService;
        this.instrumentToLessonDao = instrumentToLessonDao;
    }

    public List<InstrumentListDto> getInstruments(Integer lessonIdd) {
        return mappingService.mapList(instrumentDao.getInstrumentsByLessonIdd(lessonIdd), InstrumentListDto.class);
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentIdd) {
        var link = new InstrumentToLesson();
        link.setLessonIdd(idd);
        link.setInstrumentIdd(instrumentIdd);
        instrumentToLessonDao.insert(link);
    }
}
