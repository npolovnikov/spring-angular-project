package ru.dfsystems.spring.tutorial.dao.student;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.records.StudentRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;

@Repository
@AllArgsConstructor
public class StudentListDao implements BaseListDao<Student, StudentParams> {
    private final DSLContext jooq;

    @Override
    public Page<Student> list(PageParams<StudentParams> pageParams) {
        final StudentParams params = pageParams.getParams() == null ? new StudentParams() : pageParams.getParams();
        var listQuery = getStudentSelect(params);

        var count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Student> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Student.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<StudentRecord> getStudentSelect(StudentParams params) {
        var condition = STUDENT.DELETE_DATE.isNull();
        if (params.getIdd() != null) {
            condition = condition.and(STUDENT.IDD.like(params.getIdd()));
        }
        if (params.getFirstName() != null) {
            condition = condition.and(STUDENT.FIRST_NAME.like(params.getFirstName()));
        }
        if (params.getMiddleName() != null) {
            condition = condition.and(STUDENT.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (params.getLastName() != null) {
            condition = condition.and(STUDENT.LAST_NAME.like(params.getLastName()));
        }
        if (params.getPassport() != null) {
            condition = condition.and(STUDENT.PASSPORT.like(params.getPassport()));
        }
        var sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(STUDENT)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        var asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{STUDENT.IDD.asc()}
                    : new SortField[]{STUDENT.IDD.desc()};
        }

        var orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? STUDENT.IDD.asc() : STUDENT.IDD.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? STUDENT.CREATE_DATE.asc() : STUDENT.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
