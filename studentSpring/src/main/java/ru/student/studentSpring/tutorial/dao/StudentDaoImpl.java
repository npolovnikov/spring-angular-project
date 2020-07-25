package ru.student.studentSpring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.StudentsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;

import java.time.LocalDateTime;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Students.STUDENTS;

@Repository
public class StudentDaoImpl extends StudentsDao implements BaseDao<Students> {

    private final DSLContext jooq;

    public StudentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Students getActiveByIdd(Integer idd) {
        return jooq.select(STUDENTS.fields())
                .from(STUDENTS)
                .where(STUDENTS.IDD.eq(idd).and(STUDENTS.DELETE_DATE.isNull()))
                .fetchOneInto(Students.class);
    }

    public List<Students> getHistory(Integer idd) {
        return jooq.selectFrom(STUDENTS)
                .where(STUDENTS.IDD.eq(idd))
                .fetchInto(Students.class);
    }

    public Students create(Students student) {
        student.setId(jooq.nextval(Sequences.STUDENTS_ID_SEQ).intValue());
        if (student.getIdd() == null) {
            student.setIdd(student.getId());
        }
        student.setCreateDate(LocalDateTime.now());
        super.insert(student);
        return student;
    }

}
