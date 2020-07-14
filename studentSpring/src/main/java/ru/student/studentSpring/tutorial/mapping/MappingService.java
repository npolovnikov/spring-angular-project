package ru.student.studentSpring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.InstrumentDaoImpl;
import ru.student.studentSpring.tutorial.dao.RoomDaoImpl;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingService {
    private final ModelMapper modelMapper;
    private final RoomDaoImpl roomDao;
    private final InstrumentDaoImpl instrumentDao;

    @PostConstruct
    public void init() {
//        modelMapper.typeMap(Rooms.class, RoomDto.class)
//                .addMapping(rooms -> mapList(instrumentDao.getInstrumentsByRoom(rooms.getIdd())
//                        ,  InstrumentListDto.class), RoomDto::setInstruments)
//
//                .addMapping(rooms -> mapList(roomDao.getHistory(rooms.getIdd())
//                        , RoomHistoryDto.class), RoomDto::setHistory);
    }

    public <S, D> D map(S source, Class<D> clazz) {
        return modelMapper.map(source, clazz);
    }

    public <S, D> void map(S source, D dest) {
        modelMapper.map(source, dest);
    }

    public <S, D> List<D> mapList(List<S> sources, Class<D> clazz) {
        return sources.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
    }
}
