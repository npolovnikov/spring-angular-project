package ru.dfsystems.spring.tutorial.dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Repository
public class StudentDaoImpl extends StudentDao {

    private final DSLContext jooq;

    public StudentDaoImpl(Configuration configuration, DSLContext jooq) {
        super(configuration);
        this.jooq = jooq;
    }

    public Student getActiveByIdd(Integer idd) {
        return jooq.select(STUDENT.fields())
                .from(STUDENT)
                .where(STUDENT.IDD.eq(idd).and(STUDENT.DELETE_DATE.isNull()))
                .fetchOneInto(Student.class);
    }

    public List<Student> getHistory(Integer idd) {
        return jooq.selectFrom(STUDENT)
                .where(STUDENT.IDD.eq(idd))
                .fetchInto(Student.class);
    }

    public void create(Student student) {
        student.setId(jooq.nextval(Sequences.STUDENT_ID_SEQ));
        if (student.getIdd() == null) {
            student.setIdd(student.getId());
        }
        student.setCreateDate(LocalDateTime.now());
        super.insert(student);
    }
}
