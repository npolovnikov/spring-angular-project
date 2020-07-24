package ru.dfsystems.spring.tutorial.dao.list;

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
        if (params.getName() != null && !params.getName().isEmpty()) {
            condition = condition.and(COURSE.NAME.like(params.getName()));
        }
        if (params.getStatus() != null) {
            condition = condition.and(COURSE.STATUS.like(params.getStatus()));
        }
        if (params.getMaxCountStudent() != null) {
            condition = condition.and(COURSE.MAX_COUNT_STUDENT.like(params.getMaxCountStudent().toString()));
        }
        if (params.getCreateDateEnd() != null) {
            condition = condition.and(COURSE.END_DATE.like(params.getCreateDateEnd().toString()));
        }
        if (params.getCreateDateStart() != null) {
            condition = condition.and(COURSE.START_DATE.like(params.getCreateDateStart().toString()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(COURSE)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

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
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? COURSE.NAME.asc() : COURSE.NAME.desc());
            }
            if (order.equalsIgnoreCase("teacherId")) {
                listSortBy.add(asc ? COURSE.TEACHER_IDD.asc() : COURSE.TEACHER_IDD.desc());
            }
            if (order.equalsIgnoreCase("status")) {
                listSortBy.add(asc ? COURSE.STATUS.asc() : COURSE.STATUS.desc());
            }
            if (order.equalsIgnoreCase("courseStart")) {
                listSortBy.add(asc ? COURSE.START_DATE.asc() : COURSE.START_DATE.desc());
            }
            if (order.equalsIgnoreCase("courseEnd")) {
                listSortBy.add(asc ? COURSE.END_DATE.asc() : COURSE.END_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
