package ru.dfsystems.spring.origin.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.origin.generated.Sequences;
import ru.dfsystems.spring.origin.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Instrument;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.Tables.INSTRUMENT_TO_ROOM;
import static ru.dfsystems.spring.origin.generated.tables.Instrument.INSTRUMENT;

@Repository
public class InstrumentDaoImpl extends InstrumentDao {
    private final DSLContext jooq;

    public InstrumentDaoImpl(DSLContext jooq){
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Instrument getActiveByIdd(Integer idd) {
        return jooq.select(INSTRUMENT.fields())
                .from(INSTRUMENT)
                .where(INSTRUMENT.IDD.eq(idd).and(INSTRUMENT.DELETE_DATE.isNull()))
                .fetchOneInto(Instrument.class);
    }

    public List<Instrument> getInstrumentByRoomIdd(Integer idd){
        return jooq.select(INSTRUMENT.fields())
                .from(INSTRUMENT)
                .join(INSTRUMENT_TO_ROOM)
                .on(INSTRUMENT.IDD.eq(INSTRUMENT_TO_ROOM.INSTRUMENT_IDD))
                .where(INSTRUMENT_TO_ROOM.ROOM_IDD.eq(idd))
                .fetchInto(Instrument.class);
    }

    public void create(Instrument instrument){
        instrument.setId(jooq.nextval(Sequences.INSTRUMENT_TO_ROOM_ID_SEQ));
        if(instrument.getIdd() == null){
            instrument.setIdd(instrument.getId());
        }
        instrument.setCreateDate(LocalDateTime.now());
        super.insert(instrument);
    }

}
