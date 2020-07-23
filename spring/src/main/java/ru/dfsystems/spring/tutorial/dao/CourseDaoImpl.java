package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;
import static ru.dfsystems.spring.tutorial.generated.tables.Room.ROOM;
import static ru.dfsystems.spring.tutorial.generated.tables.StudentsToCourse.STUDENTS_TO_COURSE;
import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

@Repository
public class CourseDaoImpl extends CourseDao implements BaseDao<Course> {
    private final DSLContext jooq;

    public CourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public Course create(Course course) {
        course.setId(jooq.nextval(Sequences.COURSE_ID_SEQ));
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreatedAt(LocalDateTime.now());
        super.insert(course);
        return course;
    }

    @Override
    public Course getActiveByIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(COURSE.IDD.eq(idd).and(ROOM.DELETED_AT.isNull()))
                .fetchOneInto(Course.class);
    }

    public List<Course> getHistory(Integer idd) {
        return jooq.selectFrom(COURSE)
                .where(COURSE.IDD.eq(idd))
                .fetchInto(Course.class);
    }

    public List<Course> getCoursesByStudentIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .join(STUDENTS_TO_COURSE)
                    .on(COURSE.IDD.eq(STUDENTS_TO_COURSE.COURSE_IDD))
                .where(STUDENTS_TO_COURSE.STUDENT_IDD.eq(idd))
                .fetchInto(Course.class);
    }

    public List<Course> getCoursesByTeacherIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .where(TEACHER.IDD.eq(idd))
                .fetchInto(Course.class);
    }
}
