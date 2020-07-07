package ru.dfsystems.spring.tutorial.dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;
import static ru.dfsystems.spring.tutorial.generated.tables.InstrumentToRoom.INSTRUMENT_TO_ROOM;
import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.LessonToInstrument.LESSON_TO_INSTRUMENT;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Repository
public class LessonDaoImpl extends LessonDao {

    private final DSLContext jooq;

    public LessonDaoImpl( DSLContext jooq) {
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

    public List<Lesson> getHistory(Integer id) {
        return jooq.selectFrom(LESSON)
                .where(LESSON.ID.eq(id))
                .fetchInto(Lesson.class);
    }

    public void create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
        super.insert(lesson);
    }
}
