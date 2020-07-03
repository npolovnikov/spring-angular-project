package ru.dfsystems.spring.tutorial.dao.standard;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;

@Repository
public class UserDaoImpl extends UserDao {
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
}
