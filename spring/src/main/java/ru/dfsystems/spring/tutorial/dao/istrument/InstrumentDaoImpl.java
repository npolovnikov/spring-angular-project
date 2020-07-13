package ru.dfsystems.spring.tutorial.dao.istrument;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.INSTRUMENT;
import static ru.dfsystems.spring.tutorial.generated.Tables.INSTRUMENT_TO_ROOM;

@Repository
public class InstrumentDaoImpl extends InstrumentDao {
    private final DSLContext jooq;

    public  InstrumentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    /**
     * Возврнащает список инструментов в конкретном кабинете
     */
    public List<Instrument> getInstrumentsByRoomIdd(Integer idd) {
        return jooq.select(INSTRUMENT.fields())
                .from(INSTRUMENT)
                .join(INSTRUMENT_TO_ROOM)
                .on(INSTRUMENT.IDD.eq(INSTRUMENT_TO_ROOM.INSTRUMENT_IDD))
                .where(INSTRUMENT_TO_ROOM.ROOM_IDD.eq(idd))
                .fetchInto(Instrument.class);
    }
}
