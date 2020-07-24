package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.listDao.RoomListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class RoomService extends BaseService<RoomListDto, RoomDto, RoomParams, Room>{

    private RoomDaoImpl roomDao;
    private RoomListDao roomListDao;
    private InstrumentDaoImpl instrumentDao;
    private InstrumentToRoomDao instrumentToRoomDao;
    private MappingService mappingService;

    @Autowired
    public RoomService(RoomDaoImpl roomDao, RoomListDao roomListDao, InstrumentDaoImpl instrumentDao, InstrumentToRoomDao instrumentToRoomDao, MappingService mappingService) {
        super(mappingService, roomListDao, roomDao, RoomListDto.class, RoomDto.class, Room.class);
        this.roomDao = roomDao;
        this.roomListDao = roomListDao;
        this.instrumentDao = instrumentDao;
        this.instrumentToRoomDao = instrumentToRoomDao;
        this.mappingService = mappingService;
    }
    @Transactional
    public void putInstrument(Integer idd, Integer instrumentIdd) {
        InstrumentToRoom link = new InstrumentToRoom();
        link.setRoomIdd(idd);
        link.setInstrumentIdd(instrumentIdd);
        instrumentToRoomDao.insert(link);
    }

    @Override
    public void create(RoomDto dto) {
        super.create(dto);
    }

    @Override
    public RoomDto update(Integer idd, RoomDto dto) {
        return super.update(idd, dto);
    }
}
