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
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.generated.tables.records.TeachersRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Teachers.TEACHERS;

@Repository
@AllArgsConstructor
public class TeacherListDao  implements BaseListDao<Teachers, TeacherParams> {
    private final DSLContext jooq;

    public Page<Teachers> list(PageParams<TeacherParams> pageParams) {
        final TeacherParams params = pageParams.getParams() == null
                ? new TeacherParams() : pageParams.getParams();
        val listQuery = getTeacherSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Teachers> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Teachers.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<TeachersRecord> getTeacherSelect(TeacherParams params) {
        var condition = TEACHERS.DELETE_DATE.isNull();
        if (params.getFirstName() != null) {
            condition = condition.and(TEACHERS.FIRST_NAME.like(params.getFirstName()));
        }
        if (params.getMiddleName() != null) {
            condition = condition.and(TEACHERS.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (params.getLastName() != null) {
            condition = condition.and(TEACHERS.LAST_NAME.like(params.getLastName()));
        }
        if (params.getPassport() != null) {
            condition = condition.and(TEACHERS.PASSPORT.like(params.getPassport()));
        }

        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(TEACHERS.CREATE_DATE.between(params.getCreateDateStart(),
                    params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(TEACHERS)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{TEACHERS.IDD.asc()}
                    : new SortField[]{TEACHERS.IDD.desc()};

        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? TEACHERS.IDD.asc() : TEACHERS.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")) {
                listSortBy.add(asc ? TEACHERS.FIRST_NAME.asc() : TEACHERS.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")) {
                listSortBy.add(asc ? TEACHERS.MIDDLE_NAME.asc() : TEACHERS.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")) {
                listSortBy.add(asc ? TEACHERS.LAST_NAME.asc() : TEACHERS.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("passport")) {
                listSortBy.add(asc ? TEACHERS.PASSPORT.asc() : TEACHERS.PASSPORT.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? TEACHERS.CREATE_DATE.asc() : TEACHERS.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
