package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;
import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

@Repository
public class CourseDaoImpl extends CourseDao implements BaseDao<Course> {
    private final DSLContext jooq;

    public CourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public Course create(Course course) {
        course.setId(jooq.nextval(Sequences.ROOM_ID_SEQ));
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreateDate(LocalDateTime.now());
        super.insert(course);
        return course;
    }

    public Course getActiveByIdd(Integer id) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(COURSE.ID.eq(id))
                .fetchOneInto(Course.class);
    }

    public List<Course> getHistory(Integer idd) {
        return jooq.selectFrom(COURSE)
                .where(COURSE.IDD.eq(idd))
                .fetchInto(Course.class);
    }

    public List<Lesson> getLessonsByIdd(Integer idd){
        return jooq.selectFrom(LESSON)
                .where(LESSON.COURSE_IDD.eq(idd))
                .fetchInto(Lesson.class);
    }
}