package ru.dfsystems.spring.tutorial.dao;

import lombok.val;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.Sequences;
import ru.dfsystems.spring.tutorial.generated.tables.daos.AppUserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.AppUser;
import ru.dfsystems.spring.tutorial.generated.tables.records.AppUserRecord;
import ru.dfsystems.spring.tutorial.tools.SQLer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dfsystems.spring.tutorial.generated.tables.AppUser.APP_USER;

@Repository
public class UserDaoImpl extends AppUserDao {
    private final DSLContext jooq;

    public UserDaoImpl(Configuration configuration, DSLContext jooq) {
        super(configuration);
        this.jooq = jooq;
    }

    public Page<AppUser> list(PageParams<UserParams> pageParams) {
        val listQuery = getStudentSelect(pageParams);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<AppUser> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(AppUser.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<AppUserRecord> getStudentSelect(PageParams<UserParams> pageParams) {

        Map<String, TableField<? extends TableRecord, ?>> orderMap = new HashMap<>();
        orderMap.put("login", APP_USER.LOGIN);
        val sort = SQLer.getSortField(orderMap, APP_USER.LOGIN, pageParams.getOrderBy(), pageParams.getOrderDir());
        Condition condition = APP_USER.LOGIN.notEqual("admin");
        return jooq.selectFrom(APP_USER).where(condition).orderBy(sort);
    }

    public AppUser create(AppUser user) {
        user.setId(jooq.nextval(Sequences.APP_USER_ID_SEQ));
        user.setIsActive(false);
        super.insert(user);
        return user;
    }
}
