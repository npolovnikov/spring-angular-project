package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
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
    public Page<Teacher> list(PageParams<TeacherParams> pageParams) {
        val params = pageParams.getParams() == null ? new TeacherParams() : pageParams.getParams();
        val listQuery = getTeacherSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Teacher> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Teacher.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<TeacherRecord> getTeacherSelect(TeacherParams params){
        var condition = TEACHER.DELETED_AT.isNull();
        if (params.getFirstName() != null){
            condition = condition.and(TEACHER.FIRST_NAME.like(params.getFirstName()));
        }

        if (params.getMiddleName() != null){
            condition = condition.and(TEACHER.MIDDLE_NAME.like(params.getMiddleName()));
        }

        if (params.getLastName() != null){
            condition = condition.and(TEACHER.LAST_NAME.like(params.getLastName()));
        }

        if (params.getDateFrom() != null && params.getDateTo() != null){
            condition = condition.and(TEACHER.CREATED_AT.between(params.getDateFrom(), params.getDateTo()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(TEACHER)
                .where(condition)
                .orderBy(sort);
    }

    private SortField<?>[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null){
            return asc
                    ? new SortField[]{TEACHER.IDD.asc()}
                    : new SortField[]{TEACHER.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField<?>> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? TEACHER.IDD.asc() : TEACHER.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")){
                listSortBy.add(asc ? TEACHER.FIRST_NAME.asc() : TEACHER.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")){
                listSortBy.add(asc ? TEACHER.MIDDLE_NAME.asc() : TEACHER.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")){
                listSortBy.add(asc ? TEACHER.LAST_NAME.asc() : TEACHER.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("passport")){
                listSortBy.add(asc ? TEACHER.PASSPORT.asc() : TEACHER.PASSPORT.desc());
            }
            if (order.equalsIgnoreCase("birthDate")){
                listSortBy.add(asc ? TEACHER.BIRTH_DATE.asc() : TEACHER.BIRTH_DATE.desc());
            }
            if (order.equalsIgnoreCase("createDate")){
                listSortBy.add(asc ? TEACHER.CREATED_AT.asc() : TEACHER.CREATED_AT.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
