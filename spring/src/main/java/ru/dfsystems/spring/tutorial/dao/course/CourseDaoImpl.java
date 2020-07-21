package ru.dfsystems.spring.tutorial.dao.course;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;

import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;

@Repository
public class CourseDaoImpl extends CourseDao implements BaseDao<Course> {
    final DSLContext jooq;

    public CourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public void create(Course course) {
        course.setId(jooq.nextval(Sequences.COURSE_ID_SEQ));
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreateDate(LocalDateTime.now());
        super.insert(course);
    }
    @Override
    public Course getActiveByIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(COURSE.IDD.eq(idd).and(COURSE.DELETE_DATE.isNull()))
                .fetchOneInto(Course.class);
    }
}
