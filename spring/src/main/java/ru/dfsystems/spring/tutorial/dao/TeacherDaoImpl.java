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
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.records.TeacherRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.dfsystems.spring.tutorial.generated.Tables.TEACHER;

@Repository
    @AllArgsConstructor
    public class TeacherDaoImpl extends TeacherDao {
        private final DSLContext jooq;

        /**
         * Возвращает page учителей с выбранными параметрами
         */
        public Page<Teacher> getTeachersByParams(PageParams<TeacherParams> pageParams) {
            final TeacherParams params = pageParams.getParams() == null ? new TeacherParams() : pageParams.getParams();
            /* получаем записи, соответствующие параметрам */
            val listQuery = getTeacherSelect(params);

            List<Teacher> list = listQuery.offset(pageParams.getStart())
                    .limit(pageParams.getPage())
                    .fetchInto(Teacher.class);

            val count = jooq.selectCount()
                    .from(listQuery)
                    .fetchOne(0, Long.class);


            return new Page<>(list, count);
        }

        /**
         * Возвращает записи, соответствующие выбранным параметрам
         */
        private SelectSeekStepN<TeacherRecord> getTeacherSelect(TeacherParams params) {
            var condition = TEACHER.DELETE_DATE.isNull();
            if (!params.getFirstName().isEmpty()) {
                condition = condition.and(TEACHER.FIRST_NAME.like(params.getFirstName()));
            }
            if (!params.getMiddleName().isEmpty()) {
                condition = condition.and(TEACHER.MIDDLE_NAME.like(params.getMiddleName()));
            }
            if (!params.getLastName().isEmpty()) {
                condition = condition.and(TEACHER.LAST_NAME.like(params.getLastName()));
            }
            if (!params.getPassport().isEmpty()) {
                condition = condition.and(TEACHER.PASSPORT.like(params.getPassport()));
            }
            if (!params.getStatus().isEmpty()) {
                condition = condition.and(TEACHER.STATUS.like(params.getStatus()));
            }
            if (params.getBirthDateStart() != null && params.getBirthDateEnd() != null) {
                condition = condition.and(TEACHER.BIRTH_DATE.between(params.getBirthDateStart(), params.getBirthDateEnd()));
            }
            if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
                condition = condition.and(TEACHER.CREATE_DATE.between(params.getCreateDateStart(), params.getCreateDateEnd()));
            }
            /* получаем сортировку */
            val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());
            /* Возвращаем записи, соответствующие условию, в порядке полученной сортировки */
            return jooq.selectFrom(TEACHER)
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
                        ? new SortField[]{TEACHER.IDD.asc()}
                        : new SortField[]{TEACHER.IDD.desc()};
            }
            /* Если в orderBy перечислено несколько сортировок, получаем из них массив */
            val orderArray = orderBy.split(",");
            /* Проверяем и добавляем полученные SortField сортировки в список*/
            List<SortField> listSortBy = new ArrayList<>();
            for (val order : orderArray) {
                if (order.equalsIgnoreCase("idd")) {
                    listSortBy.add(asc ? TEACHER.IDD.asc() : TEACHER.IDD.desc());
                }
                if (order.equalsIgnoreCase("firstName")) {
                    listSortBy.add(asc ? TEACHER.FIRST_NAME.asc() : TEACHER.FIRST_NAME.desc());
                }
                if (order.equalsIgnoreCase("middleName")) {
                    listSortBy.add(asc ? TEACHER.MIDDLE_NAME.asc() : TEACHER.MIDDLE_NAME.desc());
                }
                if (order.equalsIgnoreCase("lastName")) {
                    listSortBy.add(asc ? TEACHER.LAST_NAME.asc() : TEACHER.LAST_NAME.desc());
                }
                if (order.equalsIgnoreCase("passport")) {
                    listSortBy.add(asc ? TEACHER.PASSPORT.asc() : TEACHER.PASSPORT.desc());
                }
                if (order.equalsIgnoreCase("status")) {
                    listSortBy.add(asc ? TEACHER.STATUS.asc() : TEACHER.STATUS.desc());
                }
                if (order.equalsIgnoreCase("birthDate")) {
                    listSortBy.add(asc ? TEACHER.BIRTH_DATE.asc() : TEACHER.BIRTH_DATE.desc());
                }
                if (order.equalsIgnoreCase("createDate")) {
                    listSortBy.add(asc ? TEACHER.CREATE_DATE.asc() : TEACHER.CREATE_DATE.desc());
                }
            }
            /* возвращает SortField массив с первым элементом - new SortField[0] (1ая полученная сортировка) */
            return listSortBy.toArray(new SortField[0]);
        }
}
