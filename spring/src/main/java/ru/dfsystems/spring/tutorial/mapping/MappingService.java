package ru.dfsystems.spring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingService implements BaseMapping {
    private ModelMapper modelMapper;
    private RoomDaoImpl roomDao;
    private InstrumentDaoImpl instrumentDao;
    private StudentDaoImpl studentDao;
    private CourseDaoImpl courseDao;

    @PostConstruct
    public void init() {
        //Дополнительные настройки.
        Converter<Integer, List<RoomHistoryDto>> roomHistory =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomHistoryDto.class);
        Converter<Integer, List<InstrumentListDto>> instrumentList =
                context -> mapList(instrumentDao.getInstrumentsByRoomIdd(context.getSource()), InstrumentListDto.class);

        modelMapper.typeMap(Room.class, RoomDto.class)
                .addMappings(mapper -> mapper.using(roomHistory).map(Room::getIdd, RoomDto::setHistory))
                .addMappings(mapper -> mapper.using(instrumentList).map(Room::getIdd, RoomDto::setInstruments));

        Converter<Integer, List<InstrumentHistoryDto>> instrumentHistory =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentHistoryDto.class);
        Converter<Integer, List<RoomListDto>> roomList =
                context -> mapList(roomDao.getRoomsByInstrumentIdd(context.getSource()), RoomListDto.class);

        modelMapper.typeMap(Instrument.class, InstrumentDto.class)
                .addMappings(mapper -> mapper.using(instrumentHistory).map(Instrument::getIdd, InstrumentDto::setHistory))
                .addMappings(mapper -> mapper.using(roomList).map(Instrument::getIdd, InstrumentDto::setRooms));

        Converter<Integer, List<StudentHistoryDto>> studentHistory =
                context -> mapList(studentDao.getHistory(context.getSource()), StudentHistoryDto.class);
        Converter<Integer, List<CourseListDto>> courseList =
                context -> mapList(courseDao.getCoursesByStudentIdd(context.getSource()), CourseListDto.class);

        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(studentHistory).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(courseList).map(Student::getIdd, StudentDto::setCourses));

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
