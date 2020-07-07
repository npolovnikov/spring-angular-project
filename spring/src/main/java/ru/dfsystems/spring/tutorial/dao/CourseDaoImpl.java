package ru.dfsystems.spring.tutorial.dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;
import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;
import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.LessonToInstrument.LESSON_TO_INSTRUMENT;
import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;
import static ru.dfsystems.spring.tutorial.generated.tables.StudentToCourse.STUDENT_TO_COURSE;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Repository
public class CourseDaoImpl extends CourseDao implements BaseDao<Course> {

    private final DSLContext jooq;

    public CourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public List<Student> getStudentsByCourseIdd(Integer idd) {
        return jooq.select(COURSE.fields())
                .from(COURSE)
                .join(STUDENT_TO_COURSE)
                .on(STUDENT.IDD.eq(STUDENT_TO_COURSE.STUDENT_IDD))
                .where(STUDENT_TO_COURSE.COURSE_IDD.eq(idd))
                .fetchInto(Student.class);
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
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreateDate(LocalDateTime.now());
        super.insert(course);
    }
}
