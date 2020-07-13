package ru.dfsystems.spring.tutorial.dao.lesson;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

@Repository
public class LessonDaoImpl extends LessonDao implements BaseDao<Lesson> {
    final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
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

    @Override
    public List<Lesson> getHistory(Integer idd) {
        return jooq.selectFrom(LESSON)
                .where(LESSON.IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    public List<Lesson> getLessonsByCourseIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.COURSE_IDD.eq(idd))
                .fetchInto(Lesson.class);
    }
}
