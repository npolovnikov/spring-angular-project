package ru.dfsystems.spring.tutorial.dao.lesson;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.security.UserContext;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.LESSON;

@Repository
public class LessonDaoImpl extends LessonDao implements BaseDao<Lesson> {
    private final DSLContext jooq;
    /* из контекста мы получаем текущего юзера */
    private UserContext userContext;

    public LessonDaoImpl(DSLContext jooq, UserContext userContext) {
        super(jooq.configuration());
        this.jooq = jooq;
        this.userContext = userContext;
    }

    /**
     * Возвращает список курсов учителя
     */
    public List<Lesson> getLessonsByCourseIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.COURSE_IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    /**
     * Возвращает урок с заданным Idd, который не помечен удаленным.
     */
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

    public Lesson create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
        if (lesson.getIdd() == null) {
            lesson.setIdd(lesson.getId());
        }
        lesson.setCreateDate(LocalDateTime.now());
        /* проставляем id текущего юзера - кто последгий раз изменял */
        lesson.setUserId(userContext.getUser().getId());
        /* вызываем из LessonDao */
        super.insert(lesson);
        return lesson;
    }
}
