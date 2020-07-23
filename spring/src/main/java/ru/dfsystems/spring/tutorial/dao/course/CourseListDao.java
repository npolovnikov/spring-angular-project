package ru.dfsystems.spring.tutorial.dao.course;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.records.CourseRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Course.COURSE;

@Repository
@AllArgsConstructor
public class CourseListDao implements BaseListDao<Course, CourseParams> {

    private final DSLContext jooq;

    @Override
    public Page<Course> list(PageParams<CourseParams> pageParams) {
        final CourseParams params = pageParams.getParams() == null ? new CourseParams() : pageParams.getParams();
        val listQuery = getCourseSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Course> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Course.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<CourseRecord> getCourseSelect(CourseParams params) {
        var condition = COURSE.DELETE_DATE.isNull();
        if (params.getIdd() != null) {
            condition = condition.and(COURSE.IDD.like(params.getIdd()));
        }
        if (params.getName() != null) {
            condition = condition.and(COURSE.NAME.like(params.getName()));
        }
        if (params.getCreateDate() != null && params.getDeleteDate() != null) {
            condition = condition.and(COURSE.CREATE_DATE.between(params.getCreateDate(), params.getDeleteDate()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(COURSE)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{COURSE.IDD.asc()}
                    : new SortField[]{COURSE.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? COURSE.IDD.asc() : COURSE.IDD.desc());
            }
            if (order.equalsIgnoreCase("maxCountStudent")) {
                listSortBy.add(asc ? COURSE.MAX_COUNT_STUDENT.asc() : COURSE.MAX_COUNT_STUDENT.desc());
            }
            if (order.equalsIgnoreCase("startDate")) {
                listSortBy.add(asc ? COURSE.START_DATE.asc() : COURSE.START_DATE.desc());
            }
            if (order.equalsIgnoreCase("startDate")) {
                listSortBy.add(asc ? COURSE.END_DATE.asc() : COURSE.END_DATE.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? COURSE.CREATE_DATE.asc() : COURSE.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
