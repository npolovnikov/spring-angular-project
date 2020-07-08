package ru.dfsystems.spring.origin.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.origin.generated.Sequences;
import ru.dfsystems.spring.origin.generated.tables.daos.UsersDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.origin.generated.tables.pojos.Users;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.origin.generated.tables.Users.USERS;

@Repository
public class UserDaoImpl extends UsersDao {
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

    public void create(Users users){
        users.setId(jooq.nextval(Sequences.USERS_ID_SEQ));
        if(users.getIdd() == null){
            users.setIdd(users.getId());
        }
        users.setCreateDate(LocalDateTime.now());
        super.insert(users);
    }

    public List<Users> getHistory(Integer idd) {
        return jooq.selectFrom(USERS)
                .where(USERS.IDD.eq(idd))
                .fetchInto(Users.class);
    }
}