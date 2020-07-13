package ru.dfsystems.spring.tutorial.dao.lesson;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.records.LessonRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.LESSON;

@Repository
@AllArgsConstructor
public class LessonListDao {
    private final DSLContext jooq;

    /**
     * Возвращает page уроков в с выбранными параметрами
     */
    public Page<Lesson> list(PageParams<LessonParams> pageParams) {
        final LessonParams params = pageParams.getParams() == null ? new LessonParams() : pageParams.getParams();
        /* получаем записи, соответствующие параметрам */
        val listQuery = getLessonSelect(params);

        List<Lesson> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Lesson.class);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);


        return new Page<>(list, count);
    }

    /**
     * Возвращает записи, соответствующие выбранным параметрам
     */
    private SelectSeekStepN<LessonRecord> getLessonSelect(LessonParams params) {
        Condition condition = DSL.noCondition();
        if (params.getName() != null) {
            condition = condition.and(LESSON.NAME.like(params.getName()));
        }
        if (params.getCourseIdd() != null) {
            condition = condition.and(LESSON.COURSE_IDD.equal((params.getCourseIdd())));
        }
        if (params.getRoomIdd() != null) {
            condition = condition.and(LESSON.ROOM_IDD.equal(params.getRoomIdd()));
        }
        if (params.getExtraInstruments() != null) {
            condition = condition.and(LESSON.EXTRA_INSTRUMENTS.like(params.getExtraInstruments()));
        }
        if (params.getLessonDateStartStart() != null && params.getLessonDateStartEnd() != null) {
            condition = condition.and(LESSON.LESSON_DATE_START.between(params.getLessonDateStartStart(), params.getLessonDateStartEnd()));
        }
        if (params.getLessonDateEndStart() != null && params.getLessonDateStartEnd() != null) {
            condition = condition.and(LESSON.LESSON_DATE_END.between(params.getLessonDateEndStart(), params.getLessonDateStartEnd()));
        }

        /* получаем сортировку */
        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());
        /* Возвращаем записи, соответствующие условию, в порядке полученной сортировки */
        return jooq.selectFrom(LESSON)
                .where(condition)
                .orderBy(sort);
    }

    /**
     * Возвращает массив спецификаций сортировок (мы возвращаем первую полученную)
     */
    private SortField[] getOrderBy(String orderBy, String orderDir) {
        /* определяем направление сортировки */
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        /* Если orderBy не заполнен, сортируем по ID */
        if (orderBy == null) {
            return asc
                    ? new SortField[]{LESSON.ID.asc()}
                    : new SortField[]{LESSON.ID.desc()};
        }
        /* Если в orderBy перечислено несколько сортировок, получаем из них массив */
        val orderArray = orderBy.split(",");
        /* Проверяем и добавляем полученные SortField сортировки в список*/
        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("id")) {
                listSortBy.add(asc ? LESSON.ID.asc() : LESSON.ID.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? LESSON.NAME.asc() : LESSON.NAME.desc());
            }
            if (order.equalsIgnoreCase("courseIdd")) {
                listSortBy.add(asc ? LESSON.COURSE_IDD.asc() : LESSON.COURSE_IDD.desc());
            }
            if (order.equalsIgnoreCase("roomIdd")) {
                listSortBy.add(asc ? LESSON.ROOM_IDD.asc() : LESSON.ROOM_IDD.desc());
            }
            if (order.equalsIgnoreCase("extraInstruments")) {
                listSortBy.add(asc ? LESSON.EXTRA_INSTRUMENTS.asc() : LESSON.EXTRA_INSTRUMENTS.desc());
            }
            if (order.equalsIgnoreCase("lessonDateStart")) {
                listSortBy.add(asc ? LESSON.LESSON_DATE_START.asc() : LESSON.LESSON_DATE_START.desc());
            }
            if (order.equalsIgnoreCase("lessonDateEnd")) {
                listSortBy.add(asc ? LESSON.LESSON_DATE_END.asc() : LESSON.LESSON_DATE_END.desc());
            }
        }
        /* возвращает SortField массив с первым элементом - new SortField[0] (1ая полученная сортировка) */
        return listSortBy.toArray(new SortField[0]);
    }
}
