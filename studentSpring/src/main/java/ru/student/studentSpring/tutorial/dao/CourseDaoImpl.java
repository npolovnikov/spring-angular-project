package ru.student.studentSpring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.CoursesDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.generated.tables.records.CoursesRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Courses.COURSES;
import static ru.student.studentSpring.tutorial.generated.tables.InstrumentsToRooms.INSTRUMENTS_TO_ROOMS;
import static ru.student.studentSpring.tutorial.generated.tables.Lessons.LESSONS;
import static ru.student.studentSpring.tutorial.generated.tables.Instruments.INSTRUMENTS;
import static ru.student.studentSpring.tutorial.generated.tables.Teachers.TEACHERS;
import static ru.student.studentSpring.tutorial.generated.tables.StudentToCourses.STUDENT_TO_COURSES;



@Repository
@AllArgsConstructor
public class CourseDaoImpl extends CoursesDao {

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
        course.setId(jooq.nextval(Sequences.COURSES_ID_SEQ));
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
