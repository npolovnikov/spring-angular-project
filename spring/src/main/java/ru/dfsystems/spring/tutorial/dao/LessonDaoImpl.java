package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.InstrumentToLesson.INSTRUMENT_TO_LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

@Repository
public class LessonDaoImpl extends LessonDao implements BaseDao<Lesson>{

    final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public List<Lesson> getLessonsByRoomIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.ROOM_IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    public List<Lesson> getLessonsByInstrumentIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .join(INSTRUMENT_TO_LESSON)
                .on(LESSON.ID.eq(INSTRUMENT_TO_LESSON.LESSON_IDD))
                .where(INSTRUMENT_TO_LESSON.INSTRUMENT_IDD.eq(idd))
                .fetchInto(Lesson.class);

    }


    @Override
    public void create(Lesson lesson) {

    }

    @Override
    public Lesson getActiveByIdd(Integer idd) {
        return null;
    }
}
