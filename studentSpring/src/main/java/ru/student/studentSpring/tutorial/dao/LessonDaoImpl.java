package ru.student.studentSpring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.LessonsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Lessons;

import java.time.LocalDateTime;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Lessons.LESSONS;

@Repository
public class LessonDaoImpl extends LessonsDao {
    private final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Lessons getActiveByIdd(Integer id) {
        return jooq.select(LESSONS.fields())
                .from(LESSONS)
                .where(LESSONS.ID.eq(id))
                .fetchOneInto(Lessons.class);
    }
    public List<Lessons> getHistory(Integer id) {
        return jooq.selectFrom(LESSONS)
                .where(LESSONS.ID.eq(id))
                .fetchInto(Lessons.class);
    }

    public void create(Lessons lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSONS_ID_SEQ).intValue());
        lesson.setLessonDateStart(LocalDateTime.now());
        super.insert(lesson);
    }
}
