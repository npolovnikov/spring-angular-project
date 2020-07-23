package ru.dfsystems.spring.tutorial.dao.user;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;

@Repository
public class UserDaoImpl extends UserDao implements BaseDao<User> {
    private final DSLContext jooq;

    public UserDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    /**
     * Возвращает кабинет с заданным Idd, который не помечен удаленным.
     */
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

    public User create(User user) {
        user.setId(jooq.nextval(Sequences.USER_ID_SEQ));
        if (user.getIdd() == null) {
            user.setIdd(user.getId());
        }
        user.setCreateDate(LocalDateTime.now());
        /* вызываем из UserDao */
        super.insert(user);
        return user;
    }
}

