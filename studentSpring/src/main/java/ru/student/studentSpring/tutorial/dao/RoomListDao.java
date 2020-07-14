package ru.student.studentSpring.tutorial.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.generated.tables.records.RoomsRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Rooms.ROOMS;

@Repository
@AllArgsConstructor
public class RoomListDao {
    private final DSLContext jooq;

    public Page<Rooms> list(PageParams<RoomParams> pageParams) {
        final RoomParams params = pageParams.getParams() == null
                ? new RoomParams() : pageParams.getParams();
        val listQuery = getRoomSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Rooms> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Rooms.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<RoomsRecord> getRoomSelect(RoomParams params) {
        var condition = ROOMS.DELETE_DATE.isNull();
        if (params.getBlock() != null) {
            condition = condition.and(ROOMS.BLOCK.like(params.getBlock()));
        }
        if (params.getNumber() != null) {
            condition = condition.and(ROOMS.NUMBER.like(params.getNumber()));
        }

        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(ROOMS.CREATE_DATE.between(params.getCreateDateStart(),
                    params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(ROOMS)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{ROOMS.IDD.asc()}
                    : new SortField[]{ROOMS.IDD.desc()};

        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? ROOMS.IDD.asc() : ROOMS.IDD.desc());
            }
            if (order.equalsIgnoreCase("block")) {
                listSortBy.add(asc ? ROOMS.BLOCK.asc() : ROOMS.BLOCK.desc());
            }
            if (order.equalsIgnoreCase("number")) {
                listSortBy.add(asc ? ROOMS.NUMBER.asc() : ROOMS.NUMBER.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? ROOMS.CREATE_DATE.asc() : ROOMS.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
