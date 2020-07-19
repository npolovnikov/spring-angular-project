package ru.dfsystems.spring.tutorial.dao;

import lombok.val;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.tools.Checker;
import ru.dfsystems.spring.tutorial.tools.SQLer;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.records.StudentRecord;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;

@Repository
public class StudentDaoImpl extends StudentDao  implements  BaseDao<Student>,BaseListDao<Student, StudentParams> {
    private final DSLContext jooq;

    public StudentDaoImpl(Configuration configuration, DSLContext jooq) {
        super(configuration);
        this.jooq = jooq;
    }

    @Override
    public Page<Student> list(PageParams<StudentParams> pageParams) {
        val listQuery = getStudentSelect(pageParams);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Student> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Student.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<StudentRecord> getStudentSelect(PageParams<StudentParams> pageParams) {
        final StudentParams params = pageParams.getParams() == null ? new StudentParams() : pageParams.getParams();
        Condition condition = STUDENT.DELETE_DATE.isNull();
        String firstName = params.getFirstName();
        String lastName = params.getLastName();
        String middleName = params.getMiddleName();
        if (Checker.checkEmpty(firstName)) {
            condition = condition.and(STUDENT.FIRST_NAME.like(firstName));
        }
        if (Checker.checkEmpty(lastName)) {
            condition = condition.and(STUDENT.LAST_NAME.like(lastName));
        }
        if (Checker.checkEmpty(middleName)) {
            condition = condition.and(STUDENT.MIDDLE_NAME.like(middleName));
        }

        Map<String, TableField<? extends TableRecord, ?>> orderMap = new HashMap<>();
        orderMap.put("idd", STUDENT.IDD);
        orderMap.put("firstName", STUDENT.FIRST_NAME);
        orderMap.put("lastName", STUDENT.LAST_NAME);
        orderMap.put("middleName", STUDENT.MIDDLE_NAME);
        orderMap.put("birthDate", STUDENT.BIRTH_DATE);
        val sort = SQLer.getSortField(orderMap, STUDENT.IDD, pageParams.getOrderBy(), pageParams.getOrderDir());

        return jooq.selectFrom(STUDENT)
                .where(condition)
                .orderBy(sort);
    }

    @Override
    public Student getActiveByIdd(Integer idd) {
        return jooq.select(STUDENT.fields())
                .from(STUDENT)
                .where(STUDENT.IDD.eq(idd).and(STUDENT.DELETE_DATE.isNull()))
                .fetchOneInto(Student.class);
    }

    @Override
    public Student create(Student student) {
        student.setId(jooq.nextval(Sequences.STUDENT_ID_SEQ));
        if (student.getIdd() == null) {
            student.setIdd(student.getId());
        }
        student.setCreateDate(LocalDateTime.now());
        super.insert(student);
        return student;
    }

    public List<Student> getHistory(Integer idd) {
        return jooq.selectFrom(STUDENT)
                .where(STUDENT.IDD.eq(idd))
                .fetchInto(Student.class);
    }

}
