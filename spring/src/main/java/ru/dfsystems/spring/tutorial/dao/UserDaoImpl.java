package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.records.UserRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;

@Repository
@AllArgsConstructor
public class UserDaoImpl extends UserDao {
    private final DSLContext jooq;

    public User getActiveByIdd(Integer id) {
        return jooq.select(USER.fields())
                .from(USER)
                .where(USER.ID.eq(id).and(USER.DELETE_DATE.isNull()))
                .fetchOneInto(User.class);
    }

    public Page<User> getUsersByParams(PageParams<UserParams> pageParams) {
        final UserParams params = pageParams.getParams() == null ? new UserParams() : pageParams.getParams();
        val listQuery = getUserSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<User> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(User.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<UserRecord> getUserSelect(UserParams params){
        var condition = USER.DELETE_DATE.isNull();
        if (!params.getMiddleName().isEmpty()){
            condition = condition.and(USER.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (!params.getPassport().isEmpty()){
            condition = condition.and(USER.PASSPORT.like(params.getPassport()));
        }
        if (!params.getStatus().isEmpty()){
            condition = condition.and(USER.STATUS.like(params.getStatus()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null){
            condition = condition.and(USER.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(USER)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null){
            return asc
                    ? new SortField[]{USER.ID.asc()}
                    : new SortField[]{USER.ID.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? USER.ID.asc() : USER.ID.desc());
            }
            if (order.equalsIgnoreCase("block")){
                listSortBy.add(asc ? USER.MIDDLE_NAME.asc() : USER.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("number")){
                listSortBy.add(asc ? USER.PASSPORT.asc() : USER.PASSPORT.desc());
            }
            if (order.equalsIgnoreCase("number")){
                listSortBy.add(asc ? USER.STATUS.asc() : USER.STATUS.desc());
            }
            if (order.equalsIgnoreCase("createDate")){
                listSortBy.add(asc ? USER.CREATE_DATE.asc() : USER.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
