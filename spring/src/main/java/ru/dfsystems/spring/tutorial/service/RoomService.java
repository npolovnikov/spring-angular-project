package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.InstrumentToRoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.list.RoomListDao;
import ru.dfsystems.spring.tutorial.dao.standard.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dto.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService extends BaseService<RoomListDto, RoomDto, RoomParams, Room> {

    private InstrumentToRoomDaoImpl instrumentToRoomDao;

    @Autowired
    public RoomService(RoomListDao roomListDao, RoomDaoImpl roomDao, InstrumentToRoomDaoImpl instrumentToRoomDao, MappingService mappingService) {
        super(mappingService, roomListDao, roomDao, RoomListDto.class, RoomDto.class, Room.class);
        this.instrumentToRoomDao = instrumentToRoomDao;
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentIdd) {
        InstrumentToRoom link = new InstrumentToRoom();
        link.setRoomIdd(idd);
        link.setInstrumentIdd(instrumentIdd);
        instrumentToRoomDao.insert(link);
    }
}
