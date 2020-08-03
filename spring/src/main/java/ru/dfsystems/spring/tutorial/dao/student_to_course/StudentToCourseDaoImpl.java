package ru.dfsystems.spring.tutorial.dao.student_to_course;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.STUDENT_TO_COURSE;

@Repository
public class StudentToCourseDaoImpl extends StudentToCourseDao {
    private final DSLContext jooq;

    public StudentToCourseDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    /**
     * Удаляет рум-инструмент записи
     */
    public void deleteByStudentAndCourseIdd(Integer studentIdd, List<Integer> iddsToBeDelete) {
        List<Integer> ids = jooq.select(STUDENT_TO_COURSE.ID)
                .from(STUDENT_TO_COURSE)
                .where(STUDENT_TO_COURSE.STUDENT_IDD.eq(studentIdd)
                        .and(STUDENT_TO_COURSE.COURSE_IDD.in(iddsToBeDelete)))
                .fetchInto(Integer.class);

        super.deleteById(ids);
    }

    /**
     * Создает и добавялет рум-инструмент записи
     */
    public void createByStudentAndCourseIdd(Integer studentIdd, List<Integer> iddsToBeAdd, Integer userId) {
        iddsToBeAdd.forEach(courseIdd -> {
            StudentToCourse link = new StudentToCourse();
            link.setStudentIdd(studentIdd);
            link.setCourseIdd(courseIdd);
            link.setUserId(userId);
            super.insert(link);
        });
    }
}