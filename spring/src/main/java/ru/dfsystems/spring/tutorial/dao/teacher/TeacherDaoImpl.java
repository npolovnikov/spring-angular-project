package ru.dfsystems.spring.tutorial.dao.teacher;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.security.UserContext;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

@Repository
public class TeacherDaoImpl extends TeacherDao implements BaseDao<Teacher> {
    private final DSLContext jooq;
    /* из контекста мы получаем текущего юзера */
    private UserContext userContext;

    public TeacherDaoImpl(DSLContext jooq, UserContext userContext) {
        super(jooq.configuration());
        this.jooq = jooq;
        this.userContext = userContext;
    }

    /**
     * Возвращает кабинет с заданным Idd, который не помечен удаленным.
     */
    public Teacher getActiveByIdd(Integer idd) {
        return jooq.select(TEACHER.fields())
                .from(TEACHER)
                .where(TEACHER.IDD.eq(idd).and(TEACHER.DELETE_DATE.isNull()))
                .fetchOneInto(Teacher.class);
    }

    public List<Teacher> getHistory(Integer idd) {
        return jooq.selectFrom(TEACHER)
                .where(TEACHER.IDD.eq(idd))
                .fetchInto(Teacher.class);
    }

    public Teacher create(Teacher teacher) {
        teacher.setId(jooq.nextval(Sequences.TEACHER_ID_SEQ));
        if (teacher.getIdd() == null) {
            teacher.setIdd(teacher.getId());
        }
        teacher.setCreateDate(LocalDateTime.now());
        /* проставляем id текущего юзера - кто последгий раз изменял */
        teacher.setUserId(userContext.getUser().getId());
        /* вызываем из TeacherDao */
        super.insert(teacher);
        return teacher;
    }
}
