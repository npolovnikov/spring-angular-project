package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonToInstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.LessonToInstrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.LessonMapper;
import ru.dfsystems.spring.tutorial.mapping.RoomMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
@AllArgsConstructor
public class LessonService {

    private LessonDaoImpl lessonDao;
    private LessonListDao lessonListDao;
    private LessonToInstrumentDao lessonToInstrumentDao;

    public Page<LessonListDto> getLessonsByParams(PageParams<LessonParams> pageParams) {
        Page<Lesson> page = lessonListDao.list(pageParams);
        List<LessonListDto> list = LessonMapper.LESSON_MAPPER.lessonListToLessonListDto(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(LessonDto lessonDto) {
        lessonDao.create(LessonMapper.LESSON_MAPPER.lessonDtoToLesson(lessonDto));
    }

    public LessonDto get(Integer id) {
        return LessonMapper.LESSON_MAPPER.lessonToLessonDto(lessonDao.getById(id));
    }

    @Transactional
    public void putInstrument(Integer id, Integer instrumentIdd) {
        LessonToInstrument link = new LessonToInstrument();
        link.setLessonId(id);
        link.setInstrumentIdd(instrumentIdd);
        lessonToInstrumentDao.insert(link);
    }

    @Transactional
    public LessonDto update(Integer id, LessonDto lessonDto) {
        Lesson lesson = lessonDao.getById(id);
        if (lesson == null){
            throw new RuntimeException("");
        }
        lesson.setLessonDateEnd(LocalDateTime.now());
        lessonDao.update(lesson);

        Lesson newLesson = LessonMapper.LESSON_MAPPER.lessonDtoToLesson(lessonDto);
        newLesson.setId(lesson.getId());
        lessonDao.create(newLesson);
        return LessonMapper.LESSON_MAPPER.lessonToLessonDto(newLesson);
    }
}
