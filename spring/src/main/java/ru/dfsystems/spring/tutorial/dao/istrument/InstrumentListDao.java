package ru.dfsystems.spring.tutorial.dao.istrument;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.records.InstrumentRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.INSTRUMENT;

@Repository
@AllArgsConstructor
public class InstrumentListDao {
    private final DSLContext jooq;

    /**
     * Возвращает page инструментов с выбранными параметрами
     */
    public Page<Instrument> list(PageParams<InstrumentParams> pageParams) {
        final InstrumentParams params = pageParams.getParams() == null ? new InstrumentParams() : pageParams.getParams();
        /* получаем записи, соответствующие параметрам */
        val listQuery = getInstrumentSelect(params);

        List<Instrument> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Instrument.class);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);


        return new Page<>(list, count);
    }

    /**
     * Возвращает записи, соответствующие выбранным параметрам
     */
    private SelectSeekStepN<InstrumentRecord> getInstrumentSelect(InstrumentParams params) {
        var condition = INSTRUMENT.DELETE_DATE.isNull();
        if (params.getName() != null) {
            condition = condition.and(INSTRUMENT.NAME.like(params.getName()));
        }
        if (params.getNumber() != null) {
            condition = condition.and(INSTRUMENT.NUMBER.like(params.getNumber()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(INSTRUMENT.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }
        /* получаем сортировку */
        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());
        /* Возвращаем записи, соответствующие условию, в порядке полученной сортировки */
        return jooq.selectFrom(INSTRUMENT)
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
                    ? new SortField[]{INSTRUMENT.IDD.asc()}
                    : new SortField[]{INSTRUMENT.IDD.desc()};
        }
        /* Если в orderBy перечислено несколько сортировок, получаем из них массив */
        val orderArray = orderBy.split(",");
        /* Проверяем и добавляем полученные SortField сортировки в список*/
        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? INSTRUMENT.IDD.asc() : INSTRUMENT.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? INSTRUMENT.NAME.asc() : INSTRUMENT.NAME.desc());
            }
            if (order.equalsIgnoreCase("number")) {
                listSortBy.add(asc ? INSTRUMENT.NUMBER.asc() : INSTRUMENT.NUMBER.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? INSTRUMENT.CREATE_DATE.asc() : INSTRUMENT.CREATE_DATE.desc());
            }
        }
        /* возвращает SortField массив с первым элементом - new SortField[0] (1ая полученная сортировка) */
        return listSortBy.toArray(new SortField[0]);
    }
}
