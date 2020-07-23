package ru.dfsystems.spring.tutorial.dao.instrument;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Instrument.INSTRUMENT;

@Repository
public class InstrumentDaoImpl extends InstrumentDao implements BaseDao<Instrument> {
    final DSLContext jooq;

    public InstrumentDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public void create(Instrument instrument) {
        instrument.setId(jooq.nextval(Sequences.INSTRUMENT_ID_SEQ));
        if (instrument.getIdd() == null) {
            instrument.setIdd(instrument.getId());
        }
        instrument.setCreateDate(LocalDateTime.now());
        super.insert(instrument);
    }

    @Override
    public Instrument getActiveByIdd(Integer idd) {
        return jooq.select(INSTRUMENT.fields())
                .from(INSTRUMENT)
                .where(INSTRUMENT.IDD.eq(idd).and(INSTRUMENT.DELETE_DATE.isNull()))
                .fetchOneInto(Instrument.class);
    }

    public List<Instrument> getHistory(Integer idd) {
        return jooq.selectFrom(INSTRUMENT)
                .where(INSTRUMENT.IDD.eq(idd))
                .fetchInto(Instrument.class);
    }
}
