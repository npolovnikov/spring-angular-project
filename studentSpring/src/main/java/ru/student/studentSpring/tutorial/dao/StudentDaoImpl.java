package ru.student.studentSpring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.generated.tables.daos.StudentsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.generated.tables.records.StudentsRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Students.STUDENTS;

@Repository
@AllArgsConstructor
public class StudentDaoImpl extends StudentsDao {

    private final DSLContext jooq;

    public Students getActiveByIdd(Integer idd) {
        return jooq.select(STUDENTS.fields())
                .from(STUDENTS)
                .where(STUDENTS.IDD.eq(idd).and(STUDENTS.DELETE_DATE.isNull()))
                .fetchOneInto(Students.class);
    }

    public Page<Students> getStudentsByParam(PageParams<StudentParams> pageParams) {
        final StudentParams params = pageParams.getParams() == null
                ? new StudentParams() : pageParams.getParams();
        val listQuery = getStudentSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Students> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Students.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<StudentsRecord> getStudentSelect(StudentParams params) {
        var condition = STUDENTS.DELETE_DATE.isNull();
        if (!params.getFirstName().isEmpty()) {
            condition = condition.and(STUDENTS.FIRST_NAME.like(params.getFirstName()));
        }
        if (!params.getMiddleName().isEmpty()) {
            condition = condition.and(STUDENTS.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (!params.getLastName().isEmpty()) {
            condition = condition.and(STUDENTS.LAST_NAME.like(params.getLastName()));
        }
        if (!params.getPassport().isEmpty()) {
            condition = condition.and(STUDENTS.PASSPORT.like(params.getPassport()));
        }

        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(STUDENTS.CREATE_DATE.between(params.getCreateDateStart(),
                    params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(STUDENTS)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{STUDENTS.IDD.asc()}
                    : new SortField[]{STUDENTS.IDD.desc()};

        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? STUDENTS.IDD.asc() : STUDENTS.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")) {
                listSortBy.add(asc ? STUDENTS.FIRST_NAME.asc() : STUDENTS.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")) {
                listSortBy.add(asc ? STUDENTS.MIDDLE_NAME.asc() : STUDENTS.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")) {
                listSortBy.add(asc ? STUDENTS.LAST_NAME.asc() : STUDENTS.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("passport")) {
                listSortBy.add(asc ? STUDENTS.PASSPORT.asc() : STUDENTS.PASSPORT.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? STUDENTS.CREATE_DATE.asc() : STUDENTS.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
