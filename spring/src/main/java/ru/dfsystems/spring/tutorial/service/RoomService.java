package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomListDao roomListDao;
    private RoomDaoImpl roomDao;
    private InstrumentToRoomDao instrumentToRoomDao;
    private MappingService mappingService;

    public Page<RoomListDto> getRoomsByParams(PageParams<RoomParams> pageParams) {
        Page<Room> page = roomListDao.list(pageParams);
        List<RoomListDto> list = mappingService.mapList(page.getList(), RoomListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(RoomDto roomDto) {
        roomDao.create(mappingService.map(roomDto, Room.class));
    }

    public RoomDto get(Integer idd) {
        return mappingService.map(roomDao.getActiveByIdd(idd), RoomDto.class);
    }

    @Transactional
    public void delete(Integer idd) {
        Room room = roomDao.getActiveByIdd(idd);
        room.setDeleteDate(LocalDateTime.now());
        roomDao.update(room);
    }

    @Transactional
    public void putInstrument(Integer idd, Integer instrumentIdd) {
        InstrumentToRoom link = new InstrumentToRoom();
        link.setRoomIdd(idd);
        link.setInstrumentIdd(instrumentIdd);
        instrumentToRoomDao.insert(link);
    }

    @Transactional
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
        return mappingService.map(newRoom, RoomDto.class);
    }
}
