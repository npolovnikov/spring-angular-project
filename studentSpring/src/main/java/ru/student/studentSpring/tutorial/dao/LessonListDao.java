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
import ru.student.studentSpring.tutorial.dto.lesson.LessonParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Lessons;
import ru.student.studentSpring.tutorial.generated.tables.records.LessonsRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Lessons.LESSONS;

@Repository
@AllArgsConstructor
public class LessonListDao {
    private final DSLContext jooq;

    public Page<Lessons> list(PageParams<LessonParams> pageParams) {
        final LessonParams params = pageParams.getParams() == null
                ? new LessonParams() : pageParams.getParams();
        val listQuery = getLessonSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Lessons> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Lessons.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<LessonsRecord> getLessonSelect(LessonParams params) {
        var condition = LESSONS.LESSON_DATE_START.isNotNull();
        if (params.getName() != null) {
            condition = condition.and(LESSONS.NAME.like(params.getName()));
        }
        if (params.getDescription() != null) {
            condition = condition.and(LESSONS.DESCRIPTION.like(params.getDescription()));
        }

        if (params.getLessonDateStart() != null && params.getLessonDateEnd() != null) {
            condition = condition.and(LESSONS.LESSON_DATE_START.between(params.getLessonDateStart(),
                    params.getLessonDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(LESSONS)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{LESSONS.ID.asc()}
                    : new SortField[]{LESSONS.ID.desc()};

        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray) {
            if (order.equalsIgnoreCase("id")) {
                listSortBy.add(asc ? LESSONS.ID.asc() : LESSONS.ID.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? LESSONS.NAME.asc() : LESSONS.NAME.desc());
            }
            if (order.equalsIgnoreCase("description")) {
                listSortBy.add(asc ? LESSONS.DESCRIPTION.asc() : LESSONS.DESCRIPTION.desc());
            }
            if (order.equalsIgnoreCase("lessonDateStart")) {
                listSortBy.add(asc ? LESSONS.LESSON_DATE_START.asc() : LESSONS.LESSON_DATE_START.desc());
            }
            if (order.equalsIgnoreCase("lessonDateEnd")) {
                listSortBy.add(asc ? LESSONS.LESSON_DATE_END.asc() : LESSONS.LESSON_DATE_END.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
