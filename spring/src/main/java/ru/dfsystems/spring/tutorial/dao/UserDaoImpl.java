package ru.dfsystems.spring.tutorial.dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Repository
public class UserDaoImpl extends UserDao implements BaseDao<User> {

    private final DSLContext jooq;

    public UserDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public User getActiveByIdd(Integer idd) {
        return jooq.select(USER.fields())
                .from(USER)
                .where(USER.IDD.eq(idd).and(USER.DELETE_DATE.isNull()))
                .fetchOneInto(User.class);
    }

    public List<User> getHistory(Integer idd) {
        return jooq.selectFrom(USER)
                .where(USER.IDD.eq(idd))
                .fetchInto(User.class);
    }

    public void create(User user) {
        user.setId(jooq.nextval(Sequences.USER_ID_SEQ));
        if (user.getIdd() == null) {
            user.setIdd(user.getId());
        }
        user.setCreateDate(LocalDateTime.now());
        super.insert(user);
    }
}
