package ru.dfsystems.spring.tutorial.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.InstrumentToRoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomListDao;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.ArrayList;
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

    @Override
    public RoomDto create(RoomDto dto, Integer userId) {
        RoomDto result = super.create(dto, userId);
        mergeInstruments(dto.getIdd(), dto.getInstruments(), result.getInstruments());
        return get(result.getIdd());
    }

    @Override
    public RoomDto update(Integer idd, RoomDto dto, Integer userId) {
        RoomDto result = super.update(idd, dto, userId);
        mergeInstruments(dto.getIdd(), dto.getInstruments(), result.getInstruments());
        return get(result.getIdd());
    }

    @Override
    protected void doDelete(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        RoomDto dto = om.readValue(objectData, RoomDto.class);
        delete(dto.getIdd(), userId);
    }

    @Override
    protected void doUpdate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        RoomDto dto = om.readValue(objectData, RoomDto.class);
        update(dto.getIdd(), dto, userId);
    }

    @Override
    protected void doCreate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        RoomDto dto = om.readValue(objectData, RoomDto.class);
        create(dto, userId);
    }

    private void mergeInstruments(Integer roomIdd, List<InstrumentListDto> newInstruments, List<InstrumentListDto> oldInstruments) {
        List<Integer> newIdds = newInstruments == null ? new ArrayList<>() : newInstruments.stream().map(BaseListDto::getIdd).collect(Collectors.toList());
        List<Integer> oldIdds = newInstruments == null ? new ArrayList<>() : oldInstruments.stream().map(BaseListDto::getIdd).collect(Collectors.toList());

        List<Integer> iddsToBeDelete = oldIdds.stream().filter(o -> !newIdds.contains(o)).collect(Collectors.toList());
        List<Integer> iddsToBeAdd = newIdds.stream().filter(o -> !oldIdds.contains(o)).collect(Collectors.toList());

        instrumentToRoomDao.deleteByRoomAndInstrumentIdd(roomIdd, iddsToBeDelete);
        instrumentToRoomDao.createByRoomAndInstrumentIdd(roomIdd, iddsToBeAdd);
    }
}
