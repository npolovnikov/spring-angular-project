package ru.student.studentSpring.tutorial.dao;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.CoursesDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;

import java.time.LocalDateTime;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Courses.COURSES;
import static ru.student.studentSpring.tutorial.generated.tables.Lessons.LESSONS;
import static ru.student.studentSpring.tutorial.generated.tables.StudentToCourses.STUDENT_TO_COURSES;



@Repository
@AllArgsConstructor
public class CourseDaoImpl extends CoursesDao implements BaseDao<Courses> {

    private final DSLContext jooq;

    public Courses getActiveByIdd(Integer idd) {
        return jooq.select(COURSES.fields())
                .from(COURSES)
                .where(COURSES.IDD.eq(idd).and(COURSES.DELETE_DATE.isNull()))
                .fetchOneInto(Courses.class);
    }

    public List<Courses> getHistory(Integer idd) {
        return jooq.selectFrom(COURSES)
                .where(COURSES.IDD.eq(idd))
                .fetchInto(Courses.class);
    }

    public void create(Courses course) {
        course.setId(jooq.nextval(Sequences.COURSES_ID_SEQ).intValue());
        if (course.getIdd() == null) {
            course.setIdd(course.getId());
        }
        course.setCreateDate(LocalDateTime.now());
        super.insert(course);
    }

    public List<Courses> getCourses(Integer idd) {
        return jooq.select(COURSES.fields())
                .from(COURSES)
                .join(LESSONS)
                .on(COURSES.IDD.eq(LESSONS.COURSE_IDD))
                .where(LESSONS.COURSE_IDD.eq(idd))
                .fetchInto(Courses.class);

    }

    public List<Courses> getCoursesByStudent(Integer idd) {
        return jooq.select(COURSES.fields())
                .from(COURSES)
                .join(STUDENT_TO_COURSES)
                .on(COURSES.IDD.eq(STUDENT_TO_COURSES.COURSE_IDD))
                .where(STUDENT_TO_COURSES.COURSE_IDD.eq(idd))
                .fetchInto(Courses.class);

    }

}
