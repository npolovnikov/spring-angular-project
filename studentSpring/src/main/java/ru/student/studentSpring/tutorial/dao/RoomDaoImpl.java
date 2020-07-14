package ru.student.studentSpring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.generated.Sequences;
import ru.student.studentSpring.tutorial.generated.tables.daos.RoomsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;

import java.time.LocalDateTime;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Courses.COURSES;
import static ru.student.studentSpring.tutorial.generated.tables.Rooms.ROOMS;
import static ru.student.studentSpring.tutorial.generated.tables.Lessons.LESSONS;
import static ru.student.studentSpring.tutorial.generated.tables.Teachers.TEACHERS;


@Repository
public class RoomDaoImpl extends RoomsDao {
    private final DSLContext jooq;

    public RoomDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Rooms getActiveByIdd(Integer idd) {
        return jooq.select(ROOMS.fields())
                .from(ROOMS)
                .where(ROOMS.IDD.eq(idd).and(ROOMS.DELETE_DATE.isNull()))
                .fetchOneInto(Rooms.class);
    }

    public List<Rooms> getHistory(Integer idd) {
        return jooq.selectFrom(ROOMS)
                .where(ROOMS.IDD.eq(idd))
                .fetchInto(Rooms.class);
    }

    public void create(Rooms room) {
        room.setId(jooq.nextval(Sequences.ROOMS_ID_SEQ));
        if (room.getIdd() == null) {
            room.setIdd(room.getId());
        }
        room.setCreateDate(LocalDateTime.now());
        super.insert(room);
    }

    public List<Rooms> getRooms(Integer idd) {
        return jooq.select(ROOMS.fields())
                .from(ROOMS)
                .join(LESSONS)
                .on(ROOMS.IDD.eq(LESSONS.ROOM_IDD))
                .where(LESSONS.ROOM_IDD.eq(idd))
                .fetchInto(Rooms.class);

    }
}
