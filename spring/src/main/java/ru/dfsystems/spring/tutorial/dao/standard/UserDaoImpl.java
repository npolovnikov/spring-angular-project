package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import java.time.LocalDateTime;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;

@Repository
public class UserDaoImpl extends UserDao implements BaseDao<User> {
    private final DSLContext jooq;

    public UserDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    @Override
    public User create(User user) {
        user.setId(jooq.nextval(Sequences.USER_ID_SEQ));
        if (user.getIdd() == null) {
            user.setIdd(user.getId());
        }
        user.setCreateDate(LocalDateTime.now());
        super.insert(user);
        return user;
    }

    public User getActiveByIdd(Integer idd) {
        return jooq.select(USER.fields())
                .from(USER)
                .where(USER.IDD.eq(idd).and(USER.DELETE_DATE.isNull()))
                .fetchOneInto(User.class);
    }
}
