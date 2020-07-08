package ru.dfsystems.spring.origin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.origin.dao.RoomDaoImpl;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.origin.dto.room.RoomListDto;
import ru.dfsystems.spring.origin.dto.room.RoomParams;
import ru.dfsystems.spring.origin.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.origin.generated.tables.pojos.Room;
import ru.dfsystems.spring.origin.listDao.RoomListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomDaoImpl roomDao;
    private RoomListDao roomListDao;
    private InstrumentDaoImpl instrumentDao;
    private InstrumentToRoomDao instrumentToRoomDao;
    private MappingService mappingService;

    public Page<RoomListDto> getRoomsByParams(PageParams<RoomParams> pageParams) {
        Page <Room> page = roomListDao.list(pageParams);
        List<RoomListDto> list = mappingService.mapList(page.getList(), RoomListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(RoomDto roomDto) {
        roomDao.create(mappingService.map(roomDto, Room.class));
    }

    public RoomDto get(Integer idd) {
        RoomDto dto = mappingService.map(roomDao.getActiveByIdd(idd), RoomDto.class);
        dto.setHistory(mappingService.mapList(roomDao.getHistory(idd), RoomHistoryDto.class));
        dto.setInstruments(mappingService.mapList(instrumentDao.getInstrumentByRoomIdd(idd), InstrumentListDto.class));
        return dto;
    }

    public List<RoomHistoryDto> getHistory(Integer idd) {
        return mappingService.mapList(roomDao.getHistory(idd), RoomHistoryDto.class);
    }

    public void delete(Integer idd) {
        Room room = roomDao.getActiveByIdd(idd);
        room.setDeleteDate(LocalDateTime.now());
        roomDao.update(room);
    }

    public void putInstrument(Integer idd, Integer instrumentIdd) {
        InstrumentToRoom link = new InstrumentToRoom();
        link.setRoomIdd(idd);
        link.setInstrumentIdd(instrumentIdd);
        instrumentToRoomDao.insert(link);
    }

    public RoomDto update(Integer idd, RoomDto roomDto) {
        Room room = roomDao.getActiveByIdd(idd);
        if (room == null){
            throw new RuntimeException("");
        }
        room.setDeleteDate(LocalDateTime.now());
        roomDao.update(room);
        Room newRoom = mappingService.map(roomDto, Room.class);
        newRoom.setIdd(room.getIdd());
        roomDao.create(newRoom);
        return mappingService.map(room, RoomDto.class);
    }
}
