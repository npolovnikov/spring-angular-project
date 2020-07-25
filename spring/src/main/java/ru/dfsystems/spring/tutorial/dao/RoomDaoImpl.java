package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.daos.RoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.Room.ROOM;

@Repository
public class RoomDaoImpl extends RoomDao implements BaseDao<Room> {
    private final DSLContext jooq;

    public RoomDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Room getActiveByIdd(Integer idd) {
        return jooq.select(ROOM.fields())
                .from(ROOM)
                .where(ROOM.IDD.eq(idd).and(ROOM.DELETED_AT.isNull()))
                .fetchOneInto(Room.class);
    }

    public Room getRoomByLessonIdd(Integer idd) {
        return jooq.select(ROOM.fields())
                .from(ROOM)
                .join(LESSON).on(LESSON.ROOM_IDD.eq(ROOM.IDD))
                .where(LESSON.IDD.eq(idd).and(LESSON.DELETED_AT.isNull()))
                .fetchOneInto(Room.class);
    }

    public List<Room> getHistory(Integer idd) {
        return jooq.selectFrom(ROOM)
                    .where(ROOM.IDD.eq(idd))
                    .fetchInto(Room.class);
    }

    public Room create(Room room) {
        room.setId(jooq.nextval(Sequences.INSTRUMENT_TO_ROOM_ID_SEQ));
        if (room.getIdd() == null) {
            room.setIdd(room.getId());
        }
        room.setCreatedAt(LocalDateTime.now());
        super.insert(room);
        return room;
    }
}
