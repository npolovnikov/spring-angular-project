package ru.dfsystems.spring.tutorial.dao;

import lombok.val;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.tools.Checker;
import ru.dfsystems.spring.tutorial.tools.SQLer;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.records.TeacherRecord;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

@Repository
public class TeacherDaoImpl extends TeacherDao  implements BaseDao<Teacher>, BaseListDao<Teacher, TeacherParams>
{
    private final DSLContext jooq;

    public TeacherDaoImpl(Configuration configuration, DSLContext jooq) {
        super(configuration);
        this.jooq = jooq;
    }

    @Override
    public Page<Teacher> list(PageParams<TeacherParams> pageParams) {
        val listQuery = getTeacherSelect(pageParams);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Teacher> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Teacher.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<TeacherRecord> getTeacherSelect(PageParams<TeacherParams> pageParams) {
        final TeacherParams params = pageParams.getParams() == null ? new TeacherParams() : pageParams.getParams();
        Condition condition = TEACHER.DELETE_DATE.isNull();
        String firstName = params.getFirstName();
        String lastName = params.getLastName();
        String middleName = params.getMiddleName();
        if (Checker.checkEmpty(firstName)) {
            condition = condition.and(TEACHER.FIRST_NAME.like(firstName));
        }
        if (Checker.checkEmpty(lastName)) {
            condition = condition.and(TEACHER.LAST_NAME.like(lastName));
        }
        if (Checker.checkEmpty(middleName)) {
            condition = condition.and(TEACHER.MIDDLE_NAME.like(middleName));
        }

        Map<String, TableField<? extends TableRecord, ?>> orderMap = new HashMap<>();
        orderMap.put("idd", TEACHER.IDD);
        orderMap.put("firstName", TEACHER.FIRST_NAME);
        orderMap.put("lastName", TEACHER.LAST_NAME);
        orderMap.put("middleName", TEACHER.MIDDLE_NAME);
        val sort = SQLer.getSortField(orderMap, TEACHER.IDD, pageParams.getOrderBy(), pageParams.getOrderDir());

        return jooq.selectFrom(TEACHER)
                .where(condition)
                .orderBy(sort);
    }

    @Override
    public Teacher getActiveByIdd(Integer idd) {
        return jooq.select(TEACHER.fields())
                .from(TEACHER)
                .where(TEACHER.IDD.eq(idd).and(TEACHER.DELETE_DATE.isNull()))
                .fetchOneInto(Teacher.class);
    }

    @Override
    public Teacher create(Teacher teacher) {
        teacher.setId(jooq.nextval(Sequences.TEACHER_ID_SEQ));
        if (teacher.getIdd() == null) {
            teacher.setIdd(teacher.getId());
        }
        teacher.setCreateDate(LocalDateTime.now());
        super.insert(teacher);
        return teacher;
    }

}
