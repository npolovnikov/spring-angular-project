package ru.dfsystems.spring.origin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.origin.dto.room.RoomListDto;
import ru.dfsystems.spring.origin.dto.room.RoomParams;
import ru.dfsystems.spring.origin.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.origin.generated.tables.pojos.Room;
import ru.dfsystems.spring.origin.listDao.InstrumentListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentService {
    private InstrumentDaoImpl instrumentDao;
    private InstrumentListDao instrumentListDao;
    private MappingService mappingService;

    public Page<InstrumentListDto> getInstrumentByParams(PageParams<InstrumentParams> pageParams) {
        Page <Instrument> page = instrumentListDao.list(pageParams);
        List<InstrumentListDto> list = mappingService.mapList(page.getList(), InstrumentListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(InstrumentDto instrumentDto){
        instrumentDao.create(mappingService.map(instrumentDto, Instrument.class));
    }

    public InstrumentDto get(Integer idd){
        InstrumentDto dto = mappingService.map(instrumentDao.getActiveByIdd(idd), InstrumentDto.class);
        return dto;
    }

    public void delete(Integer idd){
        Instrument instrument = instrumentDao.getActiveByIdd(idd);
        instrument.setDeleteDate(LocalDateTime.now());
        instrumentDao.update(instrument);
    }
    
}
