package ru.dfsystems.spring.tutorial.dao.teacher;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.records.TeacherRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Teacher.TEACHER;

@Repository
@AllArgsConstructor
public class TeacherListDao implements BaseListDao<Teacher, TeacherParams> {
    private final DSLContext jooq;

    @Override
    public Page<Teacher> list(PageParams<ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams> pageParams) {
        final TeacherParams params = pageParams.getParams() == null ? new TeacherParams() : pageParams.getParams();
        var listQuery = getTeacherSelect(params);

        var count = jooq.selectCount()

                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Teacher> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Teacher.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<TeacherRecord> getTeacherSelect(TeacherParams params) {
        var condition = TEACHER.DELETE_DATE.isNull();
        if (params.getIdd() != null) {
            condition = condition.and(TEACHER.IDD.like(params.getIdd()));
        }
        if (params.getLastName() != null && params.getMiddleName() != null) {
            condition = condition.and(TEACHER.LAST_NAME.between(params.getLastName(), params.getMiddleName()));
        }
        if (params.getContacts() != null) {
            condition = condition.and(TEACHER.CONTACTS.like(params.getContacts()));
        }
        if (params.getStatus() != null) {
            condition = condition.and(TEACHER.CONTACTS.like(params.getStatus()));
        }

        var sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(TEACHER)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        var asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{TEACHER.IDD.asc()}
                    : new SortField[]{TEACHER.IDD.desc()};
        }

        var orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (var order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? TEACHER.IDD.asc() : TEACHER.IDD.desc());
            }
            if (order.equalsIgnoreCase("birthDate")) {
                listSortBy.add(asc ? TEACHER.BIRTH_DATE.asc() : TEACHER.BIRTH_DATE.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? TEACHER.CREATE_DATE.asc() : TEACHER.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}