package ru.student.studentSpring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.RoomDaoImpl;
import ru.student.studentSpring.tutorial.dao.RoomListDao;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.daos.InstrumentsToRoomsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.InstrumentsToRooms;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class RoomService extends BaseService<RoomListDto, RoomDto, RoomParams, Rooms> {

    private RoomDaoImpl roomsDao;
    private InstrumentsToRoomsDao instrumentsToRooms;
    private MappingService mappingService;

    @Autowired
    public RoomService(RoomListDao roomListDao, RoomDaoImpl roomsDao,
                       InstrumentsToRoomsDao instrumentsToRooms,
                       MappingService mappingService) {
        super(mappingService, roomListDao, RoomListDto.class, roomsDao, RoomDto.class, Rooms.class);
        this.instrumentsToRooms = instrumentsToRooms;
    }

    public List<RoomHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(roomsDao.getHistory(idd), RoomHistoryDto.class);
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentId) {
        InstrumentsToRooms link = new InstrumentsToRooms();
        link.setRoomIdd(idd);
        link.setInstrumentIdd(instrumentId);
        instrumentsToRooms.insert(link);
    }

}
