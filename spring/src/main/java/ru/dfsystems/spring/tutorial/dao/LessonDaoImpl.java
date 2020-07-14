package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Lesson.LESSON;
import static ru.dfsystems.spring.tutorial.generated.tables.Room.ROOM;
import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;

@Repository
public class LessonDaoImpl extends LessonDao implements BaseDao<Lesson> {
    private final DSLContext jooq;

    public LessonDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Lesson getActiveByIdd(Integer idd) {
        return jooq.select(LESSON.fields())
                .from(LESSON)
                .where(LESSON.IDD.eq(idd))
                .fetchOneInto(Lesson.class);
    }

    public List<Lesson> getHistory(Integer idd) {
        return jooq.selectFrom(LESSON)
                .where(LESSON.IDD.eq(idd))
                .fetchInto(Lesson.class);
    }

    public void create(Lesson lesson) {
        lesson.setId(jooq.nextval(Sequences.LESSON_ID_SEQ));
/*        if (lesson.getIdd() == null){
            lesson.setIdd(lesson.getId());
        }*/
        lesson.setCreateDate(LocalDateTime.now());
        super.insert(lesson);
    }

/*    public Room getRoomFromLesson(Integer idd) {
        return jooq.select(ROOM.fields())
                .from(ROOM)
                .where(LESSON.IDD.eq(idd).and(ROOM.IDD.eq(LESSON.ROOM_IDD)))
                .fetchOneInto(Room.class);
    }*/
}