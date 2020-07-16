package ru.student.studentSpring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.InstrumentsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;

import java.time.LocalDateTime;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Instruments.INSTRUMENTS;
import static ru.student.studentSpring.tutorial.generated.tables.InstrumentsToRooms.INSTRUMENTS_TO_ROOMS;


@Repository
public class InstrumentDaoImpl extends InstrumentsDao implements BaseDao<Instruments> {

    private final DSLContext jooq;

    public InstrumentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public List<Instruments> getInstrumentsByRoom(Integer idd) {
        return jooq.select(INSTRUMENTS.fields())
                .from(INSTRUMENTS)
                .join(INSTRUMENTS_TO_ROOMS)
                .on(INSTRUMENTS.IDD.eq(INSTRUMENTS_TO_ROOMS.INSTRUMENT_IDD))
                .where(INSTRUMENTS_TO_ROOMS.ROOM_IDD.eq(idd))
                .fetchInto(Instruments.class);

    }

    public Instruments getActiveByIdd(Integer idd) {
        return jooq.select(INSTRUMENTS.fields())
                .from(INSTRUMENTS)
                .where(INSTRUMENTS.IDD.eq(idd).and(INSTRUMENTS.DELETE_DATE.isNull()))
                .fetchOneInto(Instruments.class);
    }

    public List<Instruments> getHistory(Integer idd) {
        return jooq.selectFrom(INSTRUMENTS)
                .where(INSTRUMENTS.IDD.eq(idd))
                .fetchInto(Instruments.class);
    }

    public void create(Instruments instrument) {
        instrument.setId(jooq.nextval(Sequences.INSTRUMENTS_ID_SEQ).intValue());
        if (instrument.getIdd() == null) {
            instrument.setIdd(instrument.getId());
        }
        instrument.setCreateDate(LocalDateTime.now());
        super.insert(instrument);
    }
}
