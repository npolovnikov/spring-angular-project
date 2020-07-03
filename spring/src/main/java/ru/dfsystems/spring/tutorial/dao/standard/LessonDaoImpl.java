package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

@Repository
public class LessonDaoImpl extends LessonDao {
    private final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Lesson getActiveByIdd(Integer id) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.ID.eq(id))
                .fetchOneInto(Lesson.class);
    }
}