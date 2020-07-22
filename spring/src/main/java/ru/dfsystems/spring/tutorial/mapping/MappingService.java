package ru.dfsystems.spring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonHistoryDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingService implements BaseMapping {
    private ModelMapper modelMapper;
    private RoomDaoImpl roomDao;
    private InstrumentDaoImpl instrumentDao;
    private LessonDaoImpl lessonDao;

    @PostConstruct
    public void init() {
        //Дополнительные настройки.
        Converter<Integer, List<RoomHistoryDto>> roomHistory =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomHistoryDto.class);
        Converter<Integer, List<InstrumentListDto>> instrumentList =
                context -> mapList(instrumentDao.getInstrumentsByRoomIdd(context.getSource()), InstrumentListDto.class);

        Converter<Integer, List<LessonListDto>> lessonListByRoomIdd =
                context -> mapList(lessonDao.getLessonsByRoomIdd(context.getSource()), LessonListDto.class);

        modelMapper.typeMap(Room.class, RoomDto.class)
                .addMappings(mapper -> mapper.using(roomHistory).map(Room::getIdd, RoomDto::setHistory))
                .addMappings(mapper -> mapper.using(instrumentList).map(Room::getIdd, RoomDto::setInstruments))
                .addMappings(mapper -> mapper.using(lessonListByRoomIdd).map(Room::getIdd, RoomDto::setLessons));


        /**
         * Instrument
         */
        Converter<Integer, List<InstrumentHistoryDto>> instrumentHistory =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentHistoryDto.class);
        Converter<Integer, List<LessonListDto>> lessonListByInstrumentIdd =
                context -> mapList(lessonDao.getLessonsByInstrumentIdd(context.getSource()), LessonListDto.class);
        Converter<Integer, List<RoomListDto>> roomListByInstrumentIdd =
                context -> mapList(roomDao.getRoomsByInstrumentIdd(context.getSource()), RoomListDto.class);
        modelMapper.typeMap(Instrument.class, InstrumentDto.class)
                .addMappings(mapper -> mapper.using(instrumentHistory).map(Instrument::getIdd, InstrumentDto::setHistory))
                .addMappings(mapper -> mapper.using(lessonListByInstrumentIdd).map(Instrument::getIdd, InstrumentDto::setLessons))
                .addMappings(mapper -> mapper.using(roomListByInstrumentIdd).map(Instrument::getIdd, InstrumentDto::setRooms));

        /**
         * Lesson
         */
        //TODO Добавить маппинг курса
        Converter<Integer, List<LessonHistoryDto>> lessonHistory =
                context -> mapList(lessonDao.getHistory(context.getSource()), LessonHistoryDto.class);


        Converter<Integer, RoomListDto> roomListDto =
                context -> map(roomDao.getRoomByLessonIdd(context.getSource()), RoomListDto.class);

        modelMapper.typeMap(Lesson.class, LessonDto.class)
                .addMappings(mapper -> mapper.using(lessonHistory).map(Lesson::getIdd, LessonDto::setHistory))
                .addMappings(mapper -> mapper.using(roomListDto).map(Lesson::getIdd, LessonDto::setRoom));



    }

    public <S, D> D map(S source, Class<D> clazz) {
        return modelMapper.map(source, clazz);
    }

    public <S, D> void map(S source, D dest) {
        modelMapper.map(source, dest);
    }

    public <S, D> List<D> mapList(List<S> sources, Class<D> clazz){
        return sources.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
    }
}
