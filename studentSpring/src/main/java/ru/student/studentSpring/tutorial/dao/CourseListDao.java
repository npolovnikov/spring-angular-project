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
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.generated.tables.records.CoursesRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Courses.COURSES;

@Repository
@AllArgsConstructor
public class CourseListDao  implements BaseListDao<Courses, CoursesParams> {
    private final DSLContext jooq;

    public Page<Courses> list(PageParams<CoursesParams> pageParams) {
        final CoursesParams params = pageParams.getParams() == null
                ? new CoursesParams() : pageParams.getParams();
        val listQuery = getCourseSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Courses> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Courses.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<CoursesRecord> getCourseSelect(CoursesParams params) {
        var condition = COURSES.DELETE_DATE.isNull();
        if (params.getName() != null) {
            condition = condition.and(COURSES.NAME.like(params.getName()));
        }
        if (params.getDescription() != null) {
            condition = condition.and(COURSES.DESCRIPTION.like(params.getDescription()));
        }

        if (params.getStartDate() != null && params.getEndDate() != null) {
            condition = condition.and(COURSES.START_DATE.between(params.getStartDate(),
                    params.getEndDate()));
        }

        if (params.getStartDate() != null && params.getEndDate() != null) {
            condition = condition.and(COURSES.END_DATE.between(params.getStartDate(),
                    params.getEndDate()));
        }

        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(COURSES.CREATE_DATE.between(params.getCreateDateStart(),
                    params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(COURSES)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{COURSES.IDD.asc()}
                    : new SortField[]{COURSES.IDD.desc()};

        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? COURSES.IDD.asc() : COURSES.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? COURSES.NAME.asc() : COURSES.NAME.desc());
            }
            if (order.equalsIgnoreCase("description")) {
                listSortBy.add(asc ? COURSES.DESCRIPTION.asc() : COURSES.DESCRIPTION.desc());
            }
            if (order.equalsIgnoreCase("startDate")) {
                listSortBy.add(asc ? COURSES.START_DATE.asc() : COURSES.START_DATE.desc());
            }
            if (order.equalsIgnoreCase("endDate")) {
                listSortBy.add(asc ? COURSES.END_DATE.asc() : COURSES.END_DATE.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? COURSES.CREATE_DATE.asc() : COURSES.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
