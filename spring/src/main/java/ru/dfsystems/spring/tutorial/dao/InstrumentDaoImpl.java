package ru.dfsystems.spring.tutorial.dao;

import lombok.val;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.tools.SQLer;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.records.InstrumentRecord;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;
import static ru.dfsystems.spring.tutorial.generated.tables.InstrumentToRoom.INSTRUMENT_TO_ROOM;

@Repository
public class InstrumentDaoImpl extends InstrumentDao  implements BaseDao<Instrument>, BaseListDao<Instrument, InstrumentParams> {
    final DSLContext jooq;

    public InstrumentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public Page<Instrument> list(PageParams<InstrumentParams> pageParams) {
        return null;
    }

    public List<Instrument> getInstrumentsByRoomIdd(Integer idd) {
        return jooq.select(INSTRUMENT.fields())
                    .from(INSTRUMENT)
                        .join(INSTRUMENT_TO_ROOM)
                            .on(INSTRUMENT.IDD.eq(INSTRUMENT_TO_ROOM.INSTRUMENT_IDD))
                    .where(INSTRUMENT_TO_ROOM.ROOM_IDD.eq(idd))
                    .fetchInto(Instrument.class);

    }

    public Page<Instrument> getInstrumentsByParams(PageParams<InstrumentParams> pageParams) {
        val listQuery = getInstrumentSelect(pageParams);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Instrument> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Instrument.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<InstrumentRecord> getInstrumentSelect(PageParams<InstrumentParams> pageParams){
        final InstrumentParams params = pageParams.getParams() == null ? new InstrumentParams() : pageParams.getParams();
        Condition condition = INSTRUMENT.DELETE_DATE.isNull();
        if (!params.getNumber().isEmpty()){
            condition = condition.and(INSTRUMENT.NUMBER.like(params.getNumber()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null){
            condition = condition.and(INSTRUMENT.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }

        Map<String, TableField<? extends TableRecord, ?>> orderMap = new HashMap<>();
        orderMap.put("idd", INSTRUMENT.IDD);
        orderMap.put("number", INSTRUMENT.NUMBER);
        orderMap.put("createDate", INSTRUMENT.CREATE_DATE);
        val sort = SQLer.getSortField(orderMap, INSTRUMENT.IDD, pageParams.getOrderBy(), pageParams.getOrderDir());

        return jooq.selectFrom(INSTRUMENT)
                .where(condition)
                .orderBy(sort);
    }

    @Override
    public Instrument getActiveByIdd(Integer idd) {
        return jooq.select(INSTRUMENT.fields())
                .from(INSTRUMENT)
                .where(INSTRUMENT.IDD.eq(idd).and(INSTRUMENT.DELETE_DATE.isNull()))
                .fetchOneInto(Instrument.class);
    }

    @Override
    public Instrument create(Instrument instrument) {
        instrument.setId(jooq.nextval(Sequences.INSTRUMENT_ID_SEQ));
        if (instrument.getIdd() == null) {
            instrument.setIdd(instrument.getId());
        }
        instrument.setCreateDate(LocalDateTime.now());
        super.insert(instrument);
        return instrument;
    }
}
