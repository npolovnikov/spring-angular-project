package ru.dfsystems.spring.tutorial.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UsersDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Users;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.Users.USERS;

@Repository
public class UserDaoImpl extends UsersDao  implements BaseDao<Users> {
    private final DSLContext jooq;
    TeacherDaoImpl teacherDao;

    public UserDaoImpl(DSLContext jooq){
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Users getActiveByIdd(Integer idd) {
        return jooq.select(USERS.fields())
                .from(USERS)
                .where(USERS.IDD.eq(idd).and(USERS.DELETE_DATE.isNull()))
                .fetchOneInto(Users.class);
    }

    public Users create(Users users){
        users.setId(jooq.nextval(Sequences.USERS_ID_SEQ));
        if(users.getIdd() == null){
            users.setIdd(users.getId());
        }
        users.setCreateDate(LocalDateTime.now());
        super.insert(users);
        return users;
    }

    public List<Users> getHistory(Integer idd) {
        return jooq.selectFrom(USERS)
                .where(USERS.IDD.eq(idd))
                .fetchInto(Users.class);
    }
}