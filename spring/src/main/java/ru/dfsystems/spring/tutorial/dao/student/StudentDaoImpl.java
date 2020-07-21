package ru.dfsystems.spring.tutorial.dao.student;

import org.jooq.DSLContext;
import org.jooq.Sequence;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;

import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;

@Repository
public class StudentDaoImpl extends StudentDao implements BaseDao<Student> {
    final DSLContext jooq;

    public StudentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public void create(Student student) {
        student.setId(jooq.nextval(Sequences.STUDENT_ID_SEQ));
        if (student.getIdd() == null) {
            student.setIdd(student.getId());
        }
        student.setCreateDate(LocalDateTime.now());
        super.insert(student);
    }

    @Override
    public Student getActiveByIdd(Integer idd) {
        return jooq.select(STUDENT.fields())
                .from(STUDENT)
                .where(STUDENT.IDD.eq(idd).and(STUDENT.DELETE_DATE.isNull()))
                .fetchOneInto(Student.class);
    }
}
