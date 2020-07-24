package ru.dfsystems.spring.tutorial.dao.room;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.RoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.security.UserContext;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Room.ROOM;

@Repository
public class RoomDaoImpl extends RoomDao implements BaseDao<Room> {
    private final DSLContext jooq;
    /* из контекста мы получаем текущего юзера */
    private UserContext userContext;

    /**
     * Передаем DSLContext jooq конфигурацию RoomDaoImpl в родитель - RoomDao
     */
    public RoomDaoImpl(DSLContext jooq, UserContext userContext) {
        super(jooq.configuration());
        this.jooq = jooq;
        this.userContext = userContext;
    }

    /**
     * Возвращает кабинет с заданным Idd, который не помечен удаленным.
     */
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

    public Room create(Room room) {
        room.setId(jooq.nextval(Sequences.ROOM_ID_SEQ));
        if (room.getIdd() == null) {
            room.setIdd(room.getId());
        }
        room.setCreateDate(LocalDateTime.now());
        /* проставляем id текущего юзера - кто последгий раз изменял */
        room.setUserId(userContext.getUser().getId());
        /* вызываем из RoomDao */
        super.insert(room);
        return room;
    }
}
