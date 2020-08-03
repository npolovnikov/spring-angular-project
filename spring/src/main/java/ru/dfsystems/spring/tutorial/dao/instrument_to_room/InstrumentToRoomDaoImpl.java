package ru.dfsystems.spring.tutorial.dao.instrument_to_room;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;

import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.InstrumentToRoom.INSTRUMENT_TO_ROOM;

@Repository
public class InstrumentToRoomDaoImpl extends InstrumentToRoomDao {
    private final DSLContext jooq;

    public InstrumentToRoomDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    /**
     * Удаляет рум-инструмент записи
     */
    public void deleteByRoomAndInstrumentIdd(Integer roomIdd, List<Integer> iddsToBeDelete) {
        List<Integer> ids = jooq.select(INSTRUMENT_TO_ROOM.ID)
                .from(INSTRUMENT_TO_ROOM)
                .where(INSTRUMENT_TO_ROOM.ROOM_IDD.eq(roomIdd)
                        .and(INSTRUMENT_TO_ROOM.INSTRUMENT_IDD.in(iddsToBeDelete)))
                .fetchInto(Integer.class);

        super.deleteById(ids);
    }

    /**
     * Создает и добавялет рум-инструмент записи
     */
    public void createByRoomAndInstrumentIdd(Integer roomIdd, List<Integer> iddsToBeAdd,  Integer userId) {
        iddsToBeAdd.forEach(InstrumentIdd -> {
            InstrumentToRoom link = new InstrumentToRoom();
            link.setRoomIdd(roomIdd);
            link.setInstrumentIdd(InstrumentIdd);
            link.setUserId(userId);
            super.insert(link);
        });
    }
}