package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dao.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomListDao;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.BaseMapper;

@Service
public class RoomService extends BaseService<RoomListDto, RoomDto, RoomParams, Room> {

    private InstrumentToRoomDao instrumentToRoomDao;

    @Autowired
    public RoomService(BaseMapper<Room, RoomDto, RoomListDto> baseMapper, BaseListDao<Room, RoomParams> listDao,
                       BaseDao<Room> baseDao, InstrumentToRoomDao instrumentToRoomDao) {
        super(baseMapper, listDao, baseDao);
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
