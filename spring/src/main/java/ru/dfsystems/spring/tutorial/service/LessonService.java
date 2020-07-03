package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.ListDao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dao.ListDao.LessonListDao;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonToInstrumentsDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.*;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {
    private LessonToInstrumentsDao lessonToInstrumentsDao;
    private BaseDao<Lesson> lessonDao;
    @Autowired
    public LessonService(LessonListDao lessonListDao, BaseDao<Lesson> lessonDao, MappingService mappingService
            , LessonToInstrumentsDao lessonToInstrumentsDao) {
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
        this.lessonToInstrumentsDao = lessonToInstrumentsDao;
        this.lessonDao = lessonDao;
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentIdd) {
        LessonToInstruments link = new LessonToInstruments();
        link.setLessonIdd(idd);
        link.setInstrumentIdd(instrumentIdd);
        lessonToInstrumentsDao.insert(link);
    }

    @Transactional
    public void putCourse(Integer idd, Integer courseIdd) {
        Lesson lesson = lessonDao.getActiveByIdd(idd);
        lesson.setCourseIdd(courseIdd);
        lessonDao.update(lesson);
    }

    @Transactional
    public void putRoom(Integer idd, Integer roomIdd) {
        Lesson lesson = lessonDao.getActiveByIdd(idd);
        lesson.setRoomIdd(roomIdd);
        lessonDao.update(lesson);
    }


}
