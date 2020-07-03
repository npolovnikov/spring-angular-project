package ru.dfsystems.spring.origin.dao;

import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.lesson.LessonParams;
import ru.dfsystems.spring.origin.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.origin.generated.tables.records.LessonRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.tables.Lesson.LESSON;

public class LessonDaoImpl extends LessonDao {
    private final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Lesson getActiveByIdd(Integer id) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.ID.eq(id))
                .fetchOneInto(Lesson.class);
    }

    public Page<Lesson> getLessonsByParams(PageParams<LessonParams> pageParams) {
        final LessonParams params = pageParams.getParams() == null ? new LessonParams() : pageParams.getParams();
        val listQuery = getLessonSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Lesson> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Lesson.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<LessonRecord> getLessonSelect(LessonParams params) {
        var condition = LESSON.LESSON_DATE_END.isNotNull();
        if (!params.getName().isEmpty()) {
            condition = condition.and(LESSON.NAME.like(params.getName()));
        }
        if (params.getCourseId() != null) {
            condition = condition.and(LESSON.COURSE_IDD.like(params.getCourseId().toString()));
        }
        if (params.getRoomId() != null) {
            condition = condition.and(LESSON.ROOM_IDD.like(params.getRoomId().toString()));
        }
        if (params.getDescription().isEmpty()) {
            condition = condition.and(LESSON.DESCRIPTION.like(params.getDescription()));
        }
        if (params.getLessonDateStart() != null) {
            condition = condition.and(LESSON.LESSON_DATE_START.like(params.getLessonDateStart().toString()));
        }
        if (params.getLessonDateStart() != null) {
            condition = condition.and(LESSON.LESSON_DATE_END.like(params.getLessonDateStart().toString()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(LESSON)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{LESSON.ID.asc()}
                    : new SortField[]{LESSON.ID.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("id")) {
                listSortBy.add(asc ? LESSON.ID.asc() : LESSON.ID.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? LESSON.NAME.asc() : LESSON.NAME.desc());
            }
            if (order.equalsIgnoreCase("description")) {
                listSortBy.add(asc ? LESSON.DESCRIPTION.asc() : LESSON.DESCRIPTION.desc());
            }
            if (order.equalsIgnoreCase("roomIdd")) {
                listSortBy.add(asc ? LESSON.ROOM_IDD.asc() : LESSON.ROOM_IDD.desc());
            }
            if (order.equalsIgnoreCase("courseIdd")) {
                listSortBy.add(asc ? LESSON.COURSE_IDD.asc() : LESSON.COURSE_IDD.desc());
            }
            if (order.equalsIgnoreCase("lessonStart")) {
                listSortBy.add(asc ? LESSON.LESSON_DATE_START.asc() : LESSON.LESSON_DATE_START.desc());
            }
            if (order.equalsIgnoreCase("lessonEnd")) {
                listSortBy.add(asc ? LESSON.LESSON_DATE_END.asc() : LESSON.LESSON_DATE_END.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}