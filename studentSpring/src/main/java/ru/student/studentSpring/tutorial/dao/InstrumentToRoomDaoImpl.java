package ru.student.studentSpring.tutorial.dao;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.tables.daos.InstrumentsToRoomsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.InstrumentsToRooms;

import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.InstrumentsToRooms.INSTRUMENTS_TO_ROOMS;

@Repository
public class InstrumentToRoomDaoImpl extends InstrumentsToRoomsDao {
    private final DSLContext jooq;

    public InstrumentToRoomDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public void deleteByRoomAndInstrumentIdd(Integer roomIdd, List<Integer> iddsToBeDelete) {
        List<Integer> ids = jooq.select(INSTRUMENTS_TO_ROOMS.ID)
                .from(INSTRUMENTS_TO_ROOMS)
                .where(INSTRUMENTS_TO_ROOMS.ROOM_IDD.eq(roomIdd)
                .and(INSTRUMENTS_TO_ROOMS.INSTRUMENT_IDD.in(iddsToBeDelete)))
                .fetchInto(Integer.class);

        super.deleteById(ids);
    }

    public void createByRoomAndInstrumentIdd(Integer roomIdd, List<Integer> iddsToBeAdd) {
        iddsToBeAdd.forEach(InstrumentIdd -> {
            InstrumentsToRooms link = new InstrumentsToRooms();
            link.setRoomIdd(roomIdd);
            link.setInstrumentIdd(InstrumentIdd);
            super.insert(link);
        });
    }
}
