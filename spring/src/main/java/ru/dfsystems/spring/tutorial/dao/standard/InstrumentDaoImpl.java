package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;

@Repository
public class InstrumentDaoImpl extends InstrumentDao {
    private final DSLContext jooq;

    public InstrumentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Instrument getActiveByIdd(Integer idd) {
        return jooq.select(INSTRUMENT.fields())
                .from(INSTRUMENT)
                .where(INSTRUMENT.IDD.eq(idd).and(INSTRUMENT.DELETE_DATE.isNull()))
                .fetchOneInto(Instrument.class);
    }

    public List<Instrument> getInstrumentsByRoomIdd(Integer idd) {
//        return jooq.select(INSTRUMENT.fields())
//                    .from(INSTRUMENT)
//                        .join(INSTRUMENT_TO_ROOM)
//                            .on(INSTRUMENT.IDD.eq(INSTRUMENT_TO_ROOM.INSTRUMENT_IDD))
//                    .where(INSTRUMENT_TO_ROOM.ROOM_IDD.eq(idd))
//                    .fetchInto(Instrument.class);
        return null;
    }
}
