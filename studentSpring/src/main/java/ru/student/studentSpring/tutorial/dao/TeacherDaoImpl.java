package ru.student.studentSpring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.TeachersDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;

import java.time.LocalDateTime;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Courses.COURSES;
import static ru.student.studentSpring.tutorial.generated.tables.Teachers.TEACHERS;

@Repository
public class TeacherDaoImpl extends TeachersDao {

    private final DSLContext jooq;

    public TeacherDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Teachers getActiveByIdd(Integer idd) {
        return jooq.select(TEACHERS.fields())
                .from(TEACHERS)
                .where(TEACHERS.IDD.eq(idd).and(TEACHERS.DELETE_DATE.isNull()))
                .fetchOneInto(Teachers.class);
    }
    public List<Teachers> getHistory(Integer idd) {
        return jooq.selectFrom(TEACHERS)
                .where(TEACHERS.IDD.eq(idd))
                .fetchInto(Teachers.class);
    }

    public void create(Teachers teacher) {
        teacher.setId(jooq.nextval(Sequences.TEACHERS_ID_SEQ));
        if (teacher.getIdd() == null) {
            teacher.setIdd(teacher.getId());
        }
        teacher.setCreateDate(LocalDateTime.now());
        super.insert(teacher);
    }

    public List<Teachers> getTeachers(Integer idd) {
        return jooq.select(TEACHERS.fields())
                .from(TEACHERS)
                .join(COURSES)
                .on(TEACHERS.IDD.eq(COURSES.TEACHER_IDD))
                .where(COURSES.TEACHER_IDD.eq(idd))
                .fetchInto(Teachers.class);

    }
}
