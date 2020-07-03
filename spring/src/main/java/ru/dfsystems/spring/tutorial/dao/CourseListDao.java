package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.records.CourseRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;
import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Repository
@AllArgsConstructor
public class CourseListDao {

    private final DSLContext jooq;

    public Page<Course> list(PageParams<CourseParams> pageParams) {
        final CourseParams params = pageParams.getParams() == null ? new CourseParams() : pageParams.getParams();
        val listQuery = getCourseSelect(params);

        val count = jooq.selectCount().from(listQuery).fetchOne(0, Long.class);

        List<Course> courses = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Course.class);

        return new Page<>(courses, count);
    }

    private SelectSeekStepN<CourseRecord> getCourseSelect(CourseParams params) {
        var condition = COURSE.DELETE_DATE.isNull();
        if (params.getName() != null) {
            condition = condition.and(COURSE.NAME.eq(params.getName()));
        }
        if (params.getMaxCountStudents() != null) {
            condition = condition.and(COURSE.MAX_COUNT_STUDENT.eq(params.getMaxCountStudents()));
        }
        if (params.getStartDateBefore() != null && params.getStartDateAfter() != null) {
            condition = condition.and(COURSE.START_DATE.
                    between(params.getStartDateBefore(), params.getStartDateAfter()));
        }
        if (params.getStartDateBefore() != null && params.getStartDateAfter() == null) {
            condition = condition.and(COURSE.START_DATE.greaterThan(params.getStartDateBefore()));
        }
        if (params.getStartDateBefore() == null && params.getStartDateAfter() != null) {
            condition = condition.and(COURSE.START_DATE.lessThan(params.getStartDateAfter()));
        }
        if (params.getEndDateBefore() != null && params.getEndDateAfter() != null) {
            condition = condition.and(COURSE.END_DATE.
                    between(params.getEndDateBefore(), params.getEndDateAfter()));
        }
        if (params.getEndDateBefore() != null && params.getEndDateAfter() == null) {
            condition = condition.and(COURSE.END_DATE.greaterThan(params.getEndDateBefore()));
        }
        if (params.getEndDateBefore() == null && params.getEndDateAfter() != null) {
            condition = condition.and(COURSE.END_DATE.lessThan(params.getEndDateAfter()));
        }
        if (params.getCreateDateEnd() != null && params.getCreateDateStart() != null) {
            condition = condition.and(STUDENT.CREATE_DATE.
                    between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() == null) {
            condition = condition.and(STUDENT.CREATE_DATE.greaterThan(params.getCreateDateStart()));
        }
        if (params.getCreateDateStart() == null && params.getCreateDateEnd() != null) {
            condition = condition.and(STUDENT.CREATE_DATE.lessThan(params.getCreateDateEnd()));
        }

        var sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(COURSE).where(condition).orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc ? new SortField[]{COURSE.IDD.asc()} : new SortField[]{COURSE.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? COURSE.IDD.asc() : COURSE.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? COURSE.NAME.asc() : COURSE.NAME.desc());
            }
            if (order.equalsIgnoreCase("maxCountStudents")) {
                listSortBy.add(asc ? COURSE.MAX_COUNT_STUDENT.asc() : COURSE.MAX_COUNT_STUDENT.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? COURSE.CREATE_DATE.asc() : COURSE.CREATE_DATE.desc());
            }
        }
        return listSortBy.toArray(new SortField[0]);
    }
}
