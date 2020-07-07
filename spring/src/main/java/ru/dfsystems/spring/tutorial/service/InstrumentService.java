package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.InstrumentListDao;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.mapping.BaseMapper;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
public class InstrumentService extends BaseService<InstrumentListDto, InstrumentDto, InstrumentParams, Instrument> {

    @Autowired
    public InstrumentService(BaseMapper<Instrument, InstrumentDto, InstrumentListDto> baseMapper,
                             BaseListDao<Instrument, InstrumentParams> listDao, BaseDao<Instrument> baseDao) {
        super(baseMapper, listDao, baseDao);
    }
}
