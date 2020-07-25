package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.security.UserContext;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.InstrumentToRoom.INSTRUMENT_TO_ROOM;
import static ru.dfsystems.spring.tutorial.generated.tables.Room.ROOM;
import static ru.dfsystems.spring.tutorial.generated.tables.Student.STUDENT;
import static ru.dfsystems.spring.tutorial.generated.tables.StudentToCourse.STUDENT_TO_COURSE;

@Repository
public class StudentDaoImpl extends StudentDao implements BaseDao<Student> {
    private final DSLContext jooq;
    private UserContext userContext;

    public StudentDaoImpl(DSLContext jooq, UserContext userContext) {
        super(jooq.configuration());
        this.jooq = jooq;
        this.userContext = userContext;
    }

    public Student getActiveByIdd(Integer idd) {
        return jooq.select(STUDENT.fields())
                .from(STUDENT)
                .where(STUDENT.IDD.eq(idd).and(STUDENT.DELETE_DATE.isNull()))
                .fetchOneInto(Student.class);
    }

    public List<Student> getHistory(Integer idd) {
        return jooq.selectFrom(STUDENT)
                .where(STUDENT.IDD.eq(idd))
                .fetchInto(Student.class);
    }

    public Student create(Student student) {
        student.setId(jooq.nextval(Sequences.STUDENT_ID_SEQ));
        if (student.getIdd() == null) {
            student.setIdd(student.getId());
        }
        student.setCreateDate(LocalDateTime.now());
        //student.setUserId(userContext.getUser().getId());
        super.insert(student);
        return student;
    }

    public List<Student> getStudentByCoursetIdd(Integer idd) {
        return jooq.select(STUDENT.fields())
                .from(STUDENT)
                .join(STUDENT_TO_COURSE)
                .on(STUDENT.IDD.eq(STUDENT_TO_COURSE.STUDENT_IDD))
                .where(STUDENT_TO_COURSE.COURSE_IDD.eq(idd))
                .fetchInto(Student.class);
    }
}
