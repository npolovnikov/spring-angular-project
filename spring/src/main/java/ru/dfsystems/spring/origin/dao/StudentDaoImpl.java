package ru.dfsystems.spring.origin.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.student.StudentParams;
import ru.dfsystems.spring.origin.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Student;
import ru.dfsystems.spring.origin.generated.tables.records.StudentRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.tables.Student.STUDENT;

@Repository
@AllArgsConstructor
public class StudentDaoImpl extends StudentDao {
    private final DSLContext jooq;

    public Student getActiveByIdd(Integer idd) {
        return jooq.select(STUDENT.fields())
                .from(STUDENT)
                .where(STUDENT.IDD.eq(idd).and(STUDENT.DELETE_DATE.isNull()))
                .fetchOneInto(Student.class);
    }

    public Page<Student> getStudentByParams(PageParams<StudentParams> pageParams) {
        final StudentParams params = pageParams.getParams() == null ? new StudentParams() : pageParams.getParams();
        val listQuery = getStudentSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Student> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Student.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<StudentRecord> getStudentSelect(StudentParams params) {
        var condition = STUDENT.DELETE_DATE.isNull();
        if (!params.getFirstName().isEmpty()) {
            condition = condition.and(STUDENT.FIRST_NAME.like(params.getFirstName()));
        }
        if (!params.getMiddleName().isEmpty()) {
            condition = condition.and(STUDENT.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (!params.getLastName().isEmpty()) {
            condition = condition.and(STUDENT.LAST_NAME.like(params.getLastName()));
        }
        if (!params.getPassport().isEmpty()) {
            condition = condition.and(STUDENT.PASSPORT.like(params.getPassport()));
        }
        if (params.getBirthDateStart() != null) {
            condition = condition.and(STUDENT.BIRTH_DATE.like(String.valueOf(params.getBirthDateStart())));
        }
        if (params.getCreateDateStart() != null && params.getDeleteDateStart() != null) {
            condition = condition.and(STUDENT.CREATE_DATE.between(params.getCreateDateStart(), params.getDeleteDateStart()));
        }
        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(STUDENT).where(condition).orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{STUDENT.IDD.asc()}
                    : new SortField[]{STUDENT.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? STUDENT.IDD.asc() : STUDENT.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")) {
                listSortBy.add(asc ? STUDENT.FIRST_NAME.asc() : STUDENT.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")) {
                listSortBy.add(asc ? STUDENT.MIDDLE_NAME.asc() : STUDENT.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")) {
                listSortBy.add(asc ? STUDENT.LAST_NAME.asc() : STUDENT.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("passport")) {
                listSortBy.add(asc ? STUDENT.PASSPORT.asc() : STUDENT.PASSPORT.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? STUDENT.CREATE_DATE.asc() : STUDENT.CREATE_DATE.desc());
            }
        }
        return listSortBy.toArray(new SortField[0]);
    }
}