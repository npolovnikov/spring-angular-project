package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentsToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentsToCourse;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.StudentsToCourse.STUDENTS_TO_COURSE;

@Repository
public class StudentsToCourseDaoImpl extends StudentsToCourseDao {
    private final DSLContext jooq;

    public StudentsToCourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public void deleteByStudentAndCourseIdd(Integer studentIdd, List<Integer> iddsToBeDelete) {
        List<Integer> ids = jooq.select(STUDENTS_TO_COURSE.ID)
                .from(STUDENTS_TO_COURSE)
                .where(STUDENTS_TO_COURSE.STUDENT_IDD.eq(studentIdd)
                        .and(STUDENTS_TO_COURSE.COURSE_IDD.in(iddsToBeDelete)))
                .fetchInto(Integer.class);

        super.deleteById(ids);
    }

    public void createByStudentAndCourseIdd(Integer studentIdd, List<Integer> iddsToBeAdd) {
        iddsToBeAdd.forEach(courseIdd -> {
            StudentsToCourse link = new StudentsToCourse();
            link.setStudentIdd(studentIdd);
            link.setCourseIdd(courseIdd);
            super.insert(link);
        });
    }
}
