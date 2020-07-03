package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;

@Repository
public class CourseDaoImpl extends CourseDao {
    private final DSLContext jooq;

    public CourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
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
}