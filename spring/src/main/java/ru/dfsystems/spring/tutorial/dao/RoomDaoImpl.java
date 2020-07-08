package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.Room;
import ru.dfsystems.spring.tutorial.generated.tables.daos.RoomDao;

import static ru.dfsystems.spring.tutorial.generated.tables.Room.ROOM;

@Repository
@AllArgsConstructor
public class RoomDaoImpl extends RoomDao {
    private final DSLContext jooq;

    public Room getActiveByIdd(Integer idd) {
        return jooq.select(ROOM.fields())
                .from(ROOM)
                .where(ROOM.IDD.eq(idd).and(ROOM.DELETE_DATE.isNull()))
                .fetchOneInto(Room.class);
    }
}
