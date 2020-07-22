package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;
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

    public List<Lesson> getHistory(Integer idd) {
        return jooq.selectFrom(LESSON)
                .where(LESSON.IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    @Override
    public Lesson create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
        if (lesson.getIdd() == null) {
            lesson.setIdd(lesson.getId());
        }
        lesson.setCreateDate(LocalDateTime.now());
        super.insert(lesson);
        return lesson;
    }

    @Override
    public Lesson getActiveByIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.IDD.eq(idd).and(LESSON.DELETE_DATE.isNull()))
                .fetchOneInto(Lesson.class);
    }
}
