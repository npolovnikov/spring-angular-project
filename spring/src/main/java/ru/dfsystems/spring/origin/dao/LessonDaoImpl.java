package ru.dfsystems.spring.origin.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.origin.generated.Sequences;
import ru.dfsystems.spring.origin.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.origin.generated.tables.pojos.Room;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.tables.Lesson.LESSON;

@Repository
public class LessonDaoImpl extends LessonDao implements BaseDao <Lesson> {
    private final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public void create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
        super.insert(lesson);
    }

    public Lesson getActiveByIdd(Integer id) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.ID.eq(id))
                .fetchOneInto(Lesson.class);
    }
}