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
import ru.dfsystems.spring.tutorial.generated.tables.daos.UserDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.generated.tables.records.UserRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.USER;

@Repository
@AllArgsConstructor
public class UserDaoImpl extends UserDao {
    private final DSLContext jooq;

    /**
     * Возвращает page юзеров с выбранными параметрами
     */
    public Page<User> getUsersByParams(PageParams<UserParams> pageParams) {
        final UserParams params = pageParams.getParams() == null ? new UserParams() : pageParams.getParams();
        /* получаем записи, соответствующие параметрам */
        val listQuery = getUserSelect(params);

        List<User> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(User.class);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);


        return new Page<>(list, count);
    }

    /**
     * Возвращает записи, соответствующие выбранным параметрам
     */
    private SelectSeekStepN<UserRecord> getUserSelect(UserParams params) {
        var condition = USER.DELETE_DATE.isNull();
        if (!params.getFirstName().isEmpty()) {
            condition = condition.and(USER.FIRST_NAME.like(params.getFirstName()));
        }
        if (!params.getMiddleName().isEmpty()) {
            condition = condition.and(USER.MIDDLE_NAME.like(params.getMiddleName()));
        }
        if (!params.getLastName().isEmpty()) {
            condition = condition.and(USER.LAST_NAME.like(params.getLastName()));
        }
        if (!params.getPassport().isEmpty()) {
            condition = condition.and(USER.PASSPORT.like(params.getPassport()));
        }
        if (!params.getStatus().isEmpty()) {
            condition = condition.and(USER.STATUS.like(params.getStatus()));
        }
        if (params.getBirthDateStart() != null && params.getBirthDateEnd() != null) {
            condition = condition.and(USER.BIRTH_DATE.between(params.getBirthDateStart(), params.getBirthDateEnd()));
        }
        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(USER.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
        }
        /* получаем сортировку */
        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());
        /* Возвращаем записи, соответствующие условию, в порядке полученной сортировки */
        return jooq.selectFrom(USER)
                .where(condition)
                .orderBy(sort);
    }

    /**
     * Возвращает массив спецификаций сортировок (мы возвращаем первую полученную)
     */
    private SortField[] getOrderBy(String orderBy, String orderDir) {
        /* определяем направление сортировки */
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        /* Если orderBy не заполнен, сортируем по IDD */
        if (orderBy == null) {
            return asc
                    ? new SortField[]{USER.IDD.asc()}
                    : new SortField[]{USER.IDD.desc()};
        }
        /* Если в orderBy перечислено несколько сортировок, получаем из них массив */
        val orderArray = orderBy.split(",");
        /* Проверяем и добавляем полученные SortField сортировки в список*/
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
            if (order.equalsIgnoreCase("passport")) {
                listSortBy.add(asc ? USER.PASSPORT.asc() : USER.PASSPORT.desc());
            }
            if (order.equalsIgnoreCase("status")) {
                listSortBy.add(asc ? USER.STATUS.asc() : USER.STATUS.desc());
            }
            if (order.equalsIgnoreCase("birthDate")) {
                listSortBy.add(asc ? USER.BIRTH_DATE.asc() : USER.BIRTH_DATE.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? USER.CREATE_DATE.asc() : USER.CREATE_DATE.desc());
            }
        }
        /* возвращает SortField массив с первым элементом - new SortField[0] (1ая полученная сортировка) */
        return listSortBy.toArray(new SortField[0]);
    }
}

