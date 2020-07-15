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

import static ru.dfsystems.spring.tutorial.generated.Tables.COURSE;

@Repository
@AllArgsConstructor
public class CourseListDao implements BaseListDao<Course, CourseParams> {
    private final DSLContext jooq;

    /**
     * Возвращает page курсов с выбранными параметрами
     */
    public Page<Course> list(PageParams<CourseParams> pageParams) {
        final CourseParams params = pageParams.getParams() == null ? new CourseParams() : pageParams.getParams();
        /* получаем записи, соответствующие параметрам */
        val listQuery = getCourseSelect(params);

        /* курсы, соответствующие параметрам, выводимые в количестве "page" начиная со "start" */
        List<Course> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Course.class);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);


        return new Page<>(list, count);
    }

    /**
     * Возвращает записи, соответствующие выбранным параметрам
     */
    private SelectSeekStepN<CourseRecord> getCourseSelect(CourseParams params) {
        var condition = COURSE.DELETE_DATE.isNull();
        if (params.getName() != null) {
            condition = condition.and(COURSE.NAME.like(params.getName()));
        }
        if (params.getTeacherIdd() != null) {
            condition = condition.and(COURSE.TEACHER_IDD.equal(params.getTeacherIdd()));
        }
        if (params.getMaxCountStudent() != null) {
            condition = condition.and(COURSE.MAX_COUNT_STUDENT.equal((params.getMaxCountStudent())));
        }
        if (params.getStatus() != null) {
            condition = condition.and(COURSE.STATUS.like(params.getStatus()));
        }
        if (params.getStartDateStart() != null && params.getStartDateEnd() != null) {
            condition = condition.and(COURSE.START_DATE.between(params.getStartDateStart(), params.getStartDateEnd()));
        }
        if (params.getEndDateStart() != null && params.getEndDateEnd() != null) {
            condition = condition.and(COURSE.END_DATE.between(params.getEndDateStart(), params.getEndDateEnd()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(COURSE.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }
        /* получаем сортировку */
        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());
        /* Возвращаем записи, соответствующие условию, в порядке полученной сортировки */
        return jooq.selectFrom(COURSE)
                .where(condition)
                .orderBy(sort);
    }

    /**
     * Возвращает массив спецификаций сортировок (мы возвращаем первую полученную)
     */
    private SortField[] getOrderBy(String orderBy, String orderDir) {
        /* определяем направление сортировки */
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        /* Если orderBy не заполнен, сортируем по IDD */
        if (orderBy == null) {
            return asc
                    ? new SortField[]{COURSE.IDD.asc()}
                    : new SortField[]{COURSE.IDD.desc()};
        }
        /* Если в orderBy перечислено несколько сортировок, получаем из них массив */
        val orderArray = orderBy.split(",");
        /* Проверяем и добавляем полученные SortField сортировки в список*/
        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? COURSE.IDD.asc() : COURSE.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? COURSE.NAME.asc() : COURSE.NAME.desc());
            }
            if (order.equalsIgnoreCase("maxCountStudent")) {
                listSortBy.add(asc ? COURSE.MAX_COUNT_STUDENT.asc() : COURSE.MAX_COUNT_STUDENT.desc());
            }
            if (order.equalsIgnoreCase("status")) {
                listSortBy.add(asc ? COURSE.STATUS.asc() : COURSE.STATUS.desc());
            }
            if (order.equalsIgnoreCase("startDate")) {
                listSortBy.add(asc ? COURSE.START_DATE.asc() : COURSE.START_DATE.desc());
            }
            if (order.equalsIgnoreCase("endDate")) {
                listSortBy.add(asc ? COURSE.END_DATE.asc() : COURSE.END_DATE.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? COURSE.CREATE_DATE.asc() : COURSE.CREATE_DATE.desc());
            }
        }
        /* возвращает SortField массив с первым элементом - new SortField[0] (1ая полученная сортировка) */
        return listSortBy.toArray(new SortField[0]);
    }
}
