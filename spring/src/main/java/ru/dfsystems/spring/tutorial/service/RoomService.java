package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.room.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.room.RoomListDao;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.RoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class RoomService extends BaseService<RoomListDto, RoomDto, RoomParams, Room> {

    private RoomDao roomDao;

    @Autowired
    public RoomService(RoomListDao roomListDao, RoomDaoImpl roomDao, MappingService mappingService) {
        super(mappingService, roomListDao, roomDao, RoomListDto.class, RoomDto.class, Room.class);
        this.roomDao = roomDao;
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentIdd) {
        List<Room> roomList = roomDao.findAll();
        int maxId = 0;
        Room room = new Room();
        for (Room roomItr : roomList) {
            if (roomItr.getIdd().equals(idd) && roomItr.getId() >= maxId) {
                maxId = roomItr.getId();
                room = roomItr;
            }
        }
        room.setInstrumentIdd(instrumentIdd);
        roomDao.update(room);
    }
}
