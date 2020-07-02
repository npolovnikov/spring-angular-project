package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

@Repository
public class LessonDaoImpl extends LessonDao {

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

}
