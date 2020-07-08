package ru.dfsystems.spring.origin.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.origin.dao.RoomDaoImpl;
import ru.dfsystems.spring.origin.dto.room.RoomDto;
import ru.dfsystems.spring.origin.generated.tables.pojos.Room;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MappingService {
    private ModelMapper modelMapper;
    private RoomDaoImpl roomDao;
    private InstrumentDaoImpl instrumentDao;
    @PostConstruct
    public void init(){
        //Допполнительные настройки.
        modelMapper.typeMap(Room.class, RoomDto.class)
                .addMapping(room -> instrumentDao.getInstrumentByRoomIdd(room.getIdd())
                        , RoomDto::setInstruments)
                .addMapping(room -> roomDao.getHistory(room.getIdd())
                        , RoomDto::setHistory);
    }

    public <S, D> D map(S source, Class<D> clazz){
        return modelMapper.map(source, clazz);
    }

    public <S, D> void map(S source, D dest){
        modelMapper.map(source, dest);
    }

    public <S, D> List<D> mapList(List<S> source, Class<D> clazz){
        return source.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
    }
}
