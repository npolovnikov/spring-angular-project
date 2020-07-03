package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.records.LessonRecord;
import ru.dfsystems.spring.tutorial.generated.tables.records.InstrumentRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;
import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.LessonToInstruments.LESSON_TO_INSTRUMENTS;
import static ru.dfsystems.spring.tutorial.generated.tables.LessonToCourse.LESSON_TO_COURSE;

@Repository
public class LessonDaoImpl extends LessonDao implements BaseDao<Lesson> {
    private final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Lesson getActiveByIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.IDD.eq(idd).and(LESSON.DELETE_DATE.isNull()))
                .fetchOneInto(Lesson.class);
    }

    public List<Lesson> getHistory(Integer idd) {
        return jooq.selectFrom(LESSON)
                .where(LESSON.IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    public void create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
        if (lesson.getIdd() == null) {
            lesson.setIdd(lesson.getId());
        }
        lesson.setCreateDate(LocalDateTime.now());
        super.insert(lesson);
    }

    public List<Lesson> getLessonByInstrumentIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .join(LESSON_TO_INSTRUMENTS)
                .on(LESSON.IDD.eq(LESSON_TO_INSTRUMENTS.LESSON_IDD))
                .where(LESSON_TO_INSTRUMENTS.INSTRUMENT_IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    public List<Lesson> getLessonByCourseIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .join(LESSON_TO_COURSE)
                .on(LESSON.IDD.eq(LESSON_TO_COURSE.LESSON_IDD))
                .where(LESSON_TO_COURSE.COURSE_IDD.eq(idd))
                .fetchInto(Lesson.class);
    }
}
