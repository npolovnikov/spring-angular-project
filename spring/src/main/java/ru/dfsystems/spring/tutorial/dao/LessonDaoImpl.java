package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

import static ru.dfsystems.spring.tutorial.generated.tables.LessonToInstrument.LESSON_TO_INSTRUMENT;

import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LessonDaoImpl extends LessonDao {
    final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public List<Instrument> getInstrumentsByLessonId(Integer id) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .join(LESSON_TO_INSTRUMENT)
                .on(INSTRUMENT.IDD.eq(LESSON_TO_INSTRUMENT.INSTRUMENT_IDD))
                .where(LESSON_TO_INSTRUMENT.LESSON_ID.eq(id))
                .fetchInto(Instrument.class);
    }

    public Lesson getById(Integer id) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.ID.eq(id))
                .fetchOneInto(Lesson.class);
    }

    public void create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
        super.insert(lesson);
    }
}
