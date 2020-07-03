package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;

import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

@Repository
public class TeacherDaoImpl extends TeacherDao {
    private final DSLContext jooq;

    public TeacherDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Teacher getActiveByIdd(Integer idd) {
        return jooq.select(TEACHER.fields())
                .from(TEACHER)
                .where(TEACHER.IDD.eq(idd).and(TEACHER.DELETE_DATE.isNull()))
                .fetchOneInto(Teacher.class);
    }
}
