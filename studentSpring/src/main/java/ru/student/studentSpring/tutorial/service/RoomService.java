package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.InstrumentDaoImpl;
import ru.student.studentSpring.tutorial.dao.RoomDaoImpl;
import ru.student.studentSpring.tutorial.dao.RoomListDao;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.daos.InstrumentsToRoomsDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.InstrumentsToRooms;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomListDao roomListDao;
    private final RoomDaoImpl roomsDao;
    private final InstrumentDaoImpl instrumentDao;
    private final InstrumentsToRoomsDao instrumentsToRooms;
    private final MappingService mappingService;

    public Page<RoomListDto> getRoomsByParam(PageParams<RoomParams> pageParams) {
        Page<Rooms> page = roomListDao.list(pageParams);
        List<RoomListDto> list = mappingService.mapList(page.getList(), RoomListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(RoomDto roomDto) {
         roomsDao.create(mappingService.map(roomDto, Rooms.class));
    }

    public RoomDto get(Integer idd) {
        RoomDto dto = mappingService.map(roomsDao.getActiveByIdd(idd), RoomDto.class);
        dto.setHistory(getHistory(idd));
        dto.setInstruments(mappingService.mapList(instrumentDao.getInstrumentsByRoom(idd), InstrumentListDto.class));
        return dto;
    }

    public List<RoomHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(roomsDao.getHistory(idd), RoomHistoryDto.class);
    }

    @Transactional
    public void delete(Integer idd) {
        Rooms room = roomsDao.getActiveByIdd(idd);
        room.setDeleteDate(LocalDateTime.now());
        roomsDao.update(room);
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentId) {
        InstrumentsToRooms link = new InstrumentsToRooms();
        link.setRoomIdd(idd);
        link.setInstrumentIdd(instrumentId);
        instrumentsToRooms.insert(link);
    }

    @Transactional
    public RoomDto update(Integer idd, RoomDto roomDto) {
        Rooms room = roomsDao.getActiveByIdd(idd);
        if (room == null) {
            throw new RuntimeException("");
        }
        room.setDeleteDate(LocalDateTime.now());
        roomsDao.update(room);
        Rooms newRoom = mappingService.map(roomDto, Rooms.class);
        newRoom.setIdd(room.getIdd());
        roomsDao.create(newRoom);
        return mappingService.map(room, RoomDto.class);
    }
}
