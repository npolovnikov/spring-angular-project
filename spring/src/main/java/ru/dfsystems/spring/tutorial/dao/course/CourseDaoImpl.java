package ru.dfsystems.spring.tutorial.dao.course;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.COURSE;
import static ru.dfsystems.spring.tutorial.generated.Tables.STUDENT_TO_COURSE;

@Repository
public class CourseDaoImpl extends CourseDao implements BaseDao<Course> {
    private final DSLContext jooq;

    public CourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    /**
     * Возвращает список курсов студента
     */
    public List<Course> getCoursesByStudentIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .join(STUDENT_TO_COURSE)
                .on(COURSE.IDD.eq(STUDENT_TO_COURSE.COURSE_IDD))
                .where(STUDENT_TO_COURSE.STUDENT_IDD.eq(idd))
                .fetchInto(Course.class);
    }

    /**
     * Возвращает список курсов учителя
     */
    public List<Course> getCoursesByTeacherIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(COURSE.TEACHER_IDD.eq(idd))
                .fetchInto(Course.class);
    }

    public Course getActiveByIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(COURSE.IDD.eq(idd).and(COURSE.DELETE_DATE.isNull()))
                .fetchOneInto(Course.class);
    }

    public List<Course> getHistory(Integer idd) {
        return jooq.selectFrom(COURSE)
                .where(COURSE.IDD.eq(idd))
                .fetchInto(Course.class);
    }

    public void create(Course course) {
        course.setId(jooq.nextval(Sequences.COURSE_ID_SEQ));
        course.setIdd(course.getId());
        course.setCreateDate(LocalDateTime.now());
        super.insert(course);
    }
}
