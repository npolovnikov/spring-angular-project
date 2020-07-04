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
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Instruments;

import ru.student.studentSpring.tutorial.generated.tables.daos.InstrumentsDao;
import ru.student.studentSpring.tutorial.generated.tables.records.InstrumentsRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.student.studentSpring.tutorial.generated.tables.Instruments.INSTRUMENTS;


@Repository
@AllArgsConstructor
public class InstrumentDaoImpl extends InstrumentsDao {

    private final DSLContext jooq;

    public Instruments getActiveByIdd(Integer idd) {
        return jooq.select(INSTRUMENTS.fields())
                .from(INSTRUMENTS)
                .where(INSTRUMENTS.IDD.eq(idd).and(INSTRUMENTS.DELETE_DATE.isNull()))
                .fetchOneInto(Instruments.class);
    }

    public Page<Instruments> getInstrumentsByParam(PageParams<InstrumentParams> pageParams) {
        final InstrumentParams params = pageParams.getParams() == null
                ? new InstrumentParams() : pageParams.getParams();
        val listQuery = getInstrumentSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Instruments> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Instruments.class);
        return new Page<>(list, count);
    }

    private SelectSeekStepN<InstrumentsRecord> getInstrumentSelect(InstrumentParams params) {
        var condition = INSTRUMENTS.DELETE_DATE.isNull();
        if (!params.getName().isEmpty()) {
            condition = condition.and(INSTRUMENTS.NAME.like(params.getName()));
        }
        if (!params.getNumber().isEmpty()) {
            condition = condition.and(INSTRUMENTS.NUMBER.like(params.getNumber()));
        }

        if (params.getCreateDateStart() != null && params.getCreateDateEnd() != null) {
            condition = condition.and(INSTRUMENTS.CREATE_DATE.between(params.getCreateDateStart(),
                    params.getCreateDateEnd()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(INSTRUMENTS)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir) {
        val asc = orderDir != null && orderDir.equalsIgnoreCase("asc");

        if (orderBy == null) {
            return asc
                    ? new SortField[]{INSTRUMENTS.IDD.asc()}
                    : new SortField[]{INSTRUMENTS.IDD.desc()};

        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray) {
            if (order.equalsIgnoreCase("idd")) {
                listSortBy.add(asc ? INSTRUMENTS.IDD.asc() : INSTRUMENTS.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")) {
                listSortBy.add(asc ? INSTRUMENTS.NAME.asc() : INSTRUMENTS.NAME.desc());
            }
            if (order.equalsIgnoreCase("number")) {
                listSortBy.add(asc ? INSTRUMENTS.NUMBER.asc() : INSTRUMENTS.NUMBER.desc());
            }
            if (order.equalsIgnoreCase("createDate")) {
                listSortBy.add(asc ? INSTRUMENTS.CREATE_DATE.asc() : INSTRUMENTS.CREATE_DATE.desc());
            }
        }

        return listSortBy.toArray(new SortField[0]);
    }
}
