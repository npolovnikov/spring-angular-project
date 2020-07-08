package ru.dfsystems.spring.tutorial.dao.list;

import lombok.AllArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.generated.tables.records.UserRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.tables.User.USER;

@Repository
@AllArgsConstructor
public class UserListDao implements BaseListDao<User, UserParams> {
    private final DSLContext jooq;

    public Page<User> list(PageParams<UserParams> pageParams) {
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

    private SelectSeekStepN<UserRecord> getUserSelect(UserParams params) {
        var condition = USER.DELETE_DATE.isNull();
        if (!params.getFirstName().isEmpty()) {
            condition = condition.and(USER.FIRST_NAME.like(params.getFirstName()));
        }
        if (!params.getLastName().isEmpty()) {
            condition = condition.and(USER.LAST_NAME.like(params.getLastName()));
        }
        if (!params.getMiddleName().isEmpty()) {
            condition = condition.and(USER.MIDDLE_NAME.like(params.getLastName()));
        }
        if (params.getLogin().isEmpty()) {
            condition = condition.and(USER.LOGIN.like(params.getLogin()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(USER.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(USER)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{USER.IDD.asc()}
                    : new SortField[]{USER.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order : orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? USER.IDD.asc() : USER.IDD.desc());
            }
            if (order.equalsIgnoreCase("firstName")) {
                listSortBy.add(asc ? USER.FIRST_NAME.asc() : USER.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("middleName")) {
                listSortBy.add(asc ? USER.MIDDLE_NAME.asc() : USER.MIDDLE_NAME.desc());
            }
            if (order.equalsIgnoreCase("lastName")) {
                listSortBy.add(asc ? USER.LAST_NAME.asc() : USER.LAST_NAME.desc());
            }
            if (order.equalsIgnoreCase("login")) {
                listSortBy.add(asc ? USER.LOGIN.asc() : USER.LOGIN.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? USER.CREATE_DATE.asc() : USER.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
