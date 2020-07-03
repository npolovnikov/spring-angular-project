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
import ru.dfsystems.spring.origin.dto.teacher.TeacherParams;
import ru.dfsystems.spring.origin.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.origin.generated.tables.records.TeacherRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.tables.Teacher.TEACHER;

@Repository
@AllArgsConstructor
public class TeacherDaoImpl extends TeacherDao {
    private final DSLContext jooq;

    public Teacher getActiveByIdd(Integer idd) {
        return jooq.select(TEACHER.fields())
                .from(TEACHER)
                .where(TEACHER.IDD.eq(idd).and(TEACHER.DELETE_DATE.isNull()))
                .fetchOneInto(Teacher.class);
    }

    public Page<Teacher> getTeachersByParams(PageParams<TeacherParams> pageParams) {
        final TeacherParams params = pageParams.getParams() == null ? new TeacherParams() : pageParams.getParams();
        val listQuery = getTeacherSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Teacher> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Teacher.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<TeacherRecord> getTeacherSelect(TeacherParams params) {
        var condition = TEACHER.DELETE_DATE.isNull();
        if (!params.getFirstName().isEmpty()) {
            condition = condition.and(TEACHER.FIRST_NAME.like(params.getFirstName()));
        }
        if (!params.getLastName().isEmpty()) {
            condition = condition.and(TEACHER.LAST_NAME.like(params.getLastName()));
        }
        if (!params.getMiddleName().isEmpty()) {
            condition = condition.and(TEACHER.MIDDLE_NAME.like(params.getLastName()));
        }
        if (params.getStatus() != null) {
            condition = condition.and(TEACHER.STATUS.like(params.getStatus()));
        }
        if (params.getCreateDateStart() != null && params.getDeleteDateStart() != null) {
            condition = condition.and(TEACHER.CREATE_DATE.between(params.getCreateDateStart(), params.getDeleteDateStart()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(TEACHER)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{TEACHER.IDD.asc()}
                    : new SortField[]{TEACHER.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? TEACHER.IDD.asc() : TEACHER.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")) {
                listSortBy.add(asc ? TEACHER.FIRST_NAME.asc() : TEACHER.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")) {
                listSortBy.add(asc ? TEACHER.MIDDLE_NAME.asc() : TEACHER.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")) {
                listSortBy.add(asc ? TEACHER.LAST_NAME.asc() : TEACHER.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("status")) {
                listSortBy.add(asc ? TEACHER.STATUS.asc() : TEACHER.STATUS.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? TEACHER.CREATE_DATE.asc() : TEACHER.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }

}