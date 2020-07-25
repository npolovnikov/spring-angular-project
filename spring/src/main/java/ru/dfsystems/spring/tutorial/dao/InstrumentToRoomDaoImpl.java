package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.INSTRUMENT_TO_ROOM;

@Repository
/*@AllArgsConstructor*/
public class InstrumentToRoomDaoImpl extends InstrumentToRoomDao {
    private final DSLContext jooq;

    public InstrumentToRoomDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public void deleteByRoomAndInstrumentIdd(Integer roomIdd, List<Integer> iddToBeDelete) {
        List<Integer> ids = jooq.select(INSTRUMENT_TO_ROOM.ID)
                .from(INSTRUMENT_TO_ROOM)
                .where(INSTRUMENT_TO_ROOM.ROOM_IDD.eq(roomIdd)
                        .and(INSTRUMENT_TO_ROOM.INSTRUMENT_IDD.in(iddToBeDelete)))
                .fetchInto(Integer.class);
        super.deleteById(ids);
    }

    public void createByRoomAndInstrumentIdd(Integer roomIdd, List<Integer> iddToBeAdd) {
        iddToBeAdd.forEach(InstrumentIdd -> {
            InstrumentToRoom link = new InstrumentToRoom();
            link.setRoomIdd(roomIdd);
            link.setInstrumentIdd(InstrumentIdd);
            super.insert(link);
        });
    }
}
