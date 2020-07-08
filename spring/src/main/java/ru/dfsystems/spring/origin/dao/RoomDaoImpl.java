package ru.dfsystems.spring.origin.dao;

import org.jooq.DSLContext;
import org.jooq.Name;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.origin.generated.Sequences;
import ru.dfsystems.spring.origin.generated.tables.daos.RoomDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Room;
import ru.dfsystems.spring.origin.generated.tables.pojos.Student;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.Sequences.ROOM_ID_SEQ;
import static ru.dfsystems.spring.origin.generated.tables.Room.ROOM;

@Repository
public class RoomDaoImpl extends RoomDao {
    private final DSLContext jooq;

    public RoomDaoImpl(DSLContext jooq){
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Room getActiveByIdd(Integer idd) {
        return jooq.select(ROOM.fields())
                .from(ROOM)
                .where(ROOM.IDD.eq(idd).and(ROOM.DELETE_DATE.isNull()))
                .fetchOneInto(Room.class);
    }

    public List<Room> getHistory(Integer idd) {
        return jooq.selectFrom(ROOM)
                .where(ROOM.IDD.eq(idd))
                .fetchInto(Room.class);
    }

    public void create(Room room) {
        room.setId(jooq.nextval((Sequences.ROOM_ID_SEQ)));
        if(room.getIdd() == null){
            room.setIdd(room.getId());
        }
        room.setCreateDate(LocalDateTime.now());
        super.insert(room);
    }

}
