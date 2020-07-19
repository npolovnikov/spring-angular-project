package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.records.LessonRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;

@Repository
@AllArgsConstructor
public class LessonListDao implements BaseListDao<Lesson, LessonParams> {
    private final DSLContext jooq;

    @Override
    public Page<Lesson> list(PageParams<LessonParams> pageParams) {
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

    private SelectSeekStepN<LessonRecord> getLessonSelect(LessonParams params){
        var condition = LESSON.DELETE_DATE.isNull();
        if (params.getName() != null){
            condition = condition.and(LESSON.NAME.like(params.getName()));
        }

        //TODO Ushakov Добавить поиск по дате старта и окончания занятия, описанию занятия
        /*
        if (params.getNumber() != null){
            condition = condition.and(INSTRUMENT.NUMBER.like(params.getNumber()));
        }

         */
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null){
            condition = condition.and(LESSON.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(LESSON)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null){
            return asc
                    ? new SortField[]{LESSON.IDD.asc()}
                    : new SortField[]{LESSON.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? LESSON.IDD.asc() : LESSON.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")){
                listSortBy.add(asc ? LESSON.NAME.asc() : LESSON.NAME.desc());
            }

            //TODO Добавить сортировку по дате старта и окончания занятия, описанию занятия
            /*
            if (order.equalsIgnoreCase("number")){
                listSortBy.add(asc ? INSTRUMENT.NUMBER.asc() : INSTRUMENT.NUMBER.desc());
            }
            */
            if (order.equalsIgnoreCase("createDate")){
                listSortBy.add(asc ? LESSON.CREATE_DATE.asc() : LESSON.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }


}
