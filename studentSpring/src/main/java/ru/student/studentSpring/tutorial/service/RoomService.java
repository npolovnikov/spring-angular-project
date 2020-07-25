package ru.student.studentSpring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.InstrumentToRoomDaoImpl;
import ru.student.studentSpring.tutorial.dao.RoomDaoImpl;
import ru.student.studentSpring.tutorial.dao.RoomListDao;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.InstrumentsToRooms;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService extends BaseService<RoomListDto, RoomDto, RoomParams, Rooms> {

    private RoomDaoImpl roomsDao;
    private InstrumentToRoomDaoImpl instrumentsToRooms;
    private MappingService mappingService;

    @Autowired
    public RoomService(RoomListDao roomListDao, RoomDaoImpl roomsDao,
                       InstrumentToRoomDaoImpl instrumentsToRooms,
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

    @Override
    public RoomDto create(RoomDto dto) {
        RoomDto result = super.create(dto);
        mergeInstruments(dto.getIdd(), dto.getInstruments(), result.getInstruments());
        return get(result.getIdd());
    }


    @Override
    public RoomDto update(Integer idd, RoomDto dto) {
        RoomDto result = super.update(idd, dto);
        mergeInstruments(dto.getIdd(), dto.getInstruments(), result.getInstruments());
        return get(result.getIdd());
    }
    private void mergeInstruments(Integer roomIdd, List<InstrumentListDto> newInstruments,
                                  List<InstrumentListDto> oldInstruments) {
        List<Integer> newIdds = newInstruments.stream().map(BaseListDto::getIdd).collect(Collectors.toList());
        List<Integer> oldIdds = oldInstruments.stream().map(BaseListDto::getIdd).collect(Collectors.toList());

        List<Integer> iddsToBeDelete = oldIdds.stream().filter(o -> !newIdds.contains(o))
                .collect(Collectors.toList());
        List<Integer> iddsToBeAdd = newIdds.stream().filter(o -> !newIdds.contains(o))
                .collect(Collectors.toList());

        instrumentsToRooms.deleteByRoomAndInstrumentIdd(roomIdd, iddsToBeDelete);
        instrumentsToRooms.createByRoomAndInstrumentIdd(roomIdd, iddsToBeAdd);
    }
}
