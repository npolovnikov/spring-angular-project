package ru.dfsystems.spring.tutorial.security;

import org.jooq.DSLContext;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.AppUserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;


public class AuthUserDaoImpl extends AppUserDao implements BaseDao<AppUser> {

    private final DSLContext jooq;

    public AuthUserDaoImpl(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public AppUser create(AppUser appUser) {
        appUser.setId(jooq.nextval(Sequences.USER_ID_SEQ));
        super.insert(appUser);
        return appUser;
    }

    @Override
    public AppUser getActiveByIdd(Integer id) {
        return jooq.select(APP_USER.fields())
                .from(APP_USER)
                .where(APP_USER.ID.eq(id))
                .fetchOneInto(AppUser.class);
    }
}
