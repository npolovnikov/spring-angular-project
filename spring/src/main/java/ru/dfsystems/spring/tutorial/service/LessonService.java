package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonToInstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.LessonToInstrument;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class LessonService {
    LessonToInstrumentDao lessonToInstrument;
    private MappingService mappingService;
    private LessonDaoImpl lessonDao;
    // private InstrumentDaoImpl instrumentDao;
    private LessonListDao lessonListDao;

    public Page<LessonListDto> getLessonsByParams(PageParams<LessonParams> pageParams) {
        Page<Lesson> page = lessonListDao.list(pageParams);
        List<LessonListDto> list = mappingService.mapList(page.getList(), LessonListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public LessonDto get(Integer id) {
        LessonDto lessonDto = mappingService.map(lessonDao.getById(id), LessonDto.class);
        lessonDto.setInstruments(mappingService.mapList(lessonDao.getInstrumentsByLessonId(id), InstrumentListDto.class));
        return lessonDto;
    }
    @Transactional
    public void create(LessonDto lessonDto) {
        lessonDao.create(mappingService.map(lessonDto, Lesson.class));
    }

    @Transactional
    public void delete(Integer id) {
        Lesson lesson = lessonDao.getById(id);
        if (lesson == null){
            throw new RuntimeException("");
        }
        lessonDao.delete(lesson);
    }

    @Transactional
    public LessonDto update(Integer id, LessonDto lessonDto) {
        Lesson lesson = lessonDao.getById(id);
        if (lesson == null){
            throw new RuntimeException("");
        }
        lessonDao.update(lesson);

        Lesson newLesson = mappingService.map(lessonDto, Lesson.class);
        lessonDao.create(newLesson);
        return mappingService.map(newLesson, LessonDto.class);
    }

    @Transactional
    public void putInstrument(Integer id, Integer instrumentIdd) {
        LessonToInstrument link = new LessonToInstrument();
        link.setId(id);
        link.setInstrumentIdd(instrumentIdd);
        lessonToInstrument.insert(link);
    }
}
