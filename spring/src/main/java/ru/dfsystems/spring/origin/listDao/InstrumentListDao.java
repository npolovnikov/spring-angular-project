package ru.dfsystems.spring.origin.listDao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.origin.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.origin.generated.tables.records.InstrumentRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.tables.Instrument.INSTRUMENT;
import static ru.dfsystems.spring.origin.generated.tables.Room.ROOM;

@Repository
@AllArgsConstructor
public class InstrumentListDao {
    private final DSLContext jooq;
    public Page<Instrument> list(PageParams<InstrumentParams> pageParams){
        final InstrumentParams params = pageParams.getParams() == null ? new InstrumentParams() : pageParams.getParams();
        val listQuery = getInstrumentSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);
        List<Instrument> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Instrument.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<InstrumentRecord> getInstrumentSelect(InstrumentParams params) {
        var condition = INSTRUMENT.DELETE_DATE.isNull();
        if(params.getName() != null){
            condition = condition.and(INSTRUMENT.NAME.like(params.getName()));
        }
        if(params.getNumber() != null){
            condition = condition.and(INSTRUMENT.NUMBER.like(params.getNumber()));
        }
        if(params.getCreateDateStart() != null && params.getCreateDateEnd() != null){
            condition = condition.and(INSTRUMENT.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(INSTRUMENT)
                .where(condition)
                .orderBy(sort);

    }

    private SortField[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if(orderBy == null){
            return asc
                    ? new SortField[]{INSTRUMENT.IDD.asc()}
                    : new SortField[]{INSTRUMENT.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for(val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? INSTRUMENT.IDD.asc() : INSTRUMENT.IDD.desc());
            }
            if(order.equalsIgnoreCase(("name"))){
                listSortBy.add(asc ? INSTRUMENT.NAME.asc() : INSTRUMENT.NAME.desc());
            }
            if (order.equalsIgnoreCase("number")){
                listSortBy.add(asc ? INSTRUMENT.NUMBER.asc() : ROOM.NUMBER.desc());
            }
            if (order.equalsIgnoreCase("createDate")){
                listSortBy.add(asc ? INSTRUMENT.CREATE_DATE.asc() : INSTRUMENT.CREATE_DATE.desc());
            }
        }
        return listSortBy.toArray(new SortField[0]);
    }
}
