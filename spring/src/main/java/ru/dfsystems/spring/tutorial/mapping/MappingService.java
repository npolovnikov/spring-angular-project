package ru.dfsystems.spring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.instrument.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.room.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentListDao;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
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
    private CourseDaoImpl courseDao;
    private StudentDaoImpl studentDao;
    private TeacherDaoImpl teacherDao;

    @PostConstruct
    public void init() {
        //Дополнительные настройки.
        //Создание зависимости instrument и room
        Converter<Integer, List<RoomHistoryDto>> roomHistory =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomHistoryDto.class);
        Converter<Integer, List<InstrumentListDto>> instrumentList =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentListDto.class);

        modelMapper.typeMap(Room.class, RoomDto.class)
                .addMappings(mapper -> mapper.using(roomHistory).map(Room::getIdd, RoomDto::setHistory))
                .addMappings(mapper -> mapper.using(instrumentList).map(Room::getIdd, RoomDto::setInstruments));

        Converter<Integer, List<InstrumentHistoryDto>> instrumentHistory =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentHistoryDto.class);
        Converter<Integer, List<RoomListDto>> roomList =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomListDto.class);

        modelMapper.typeMap(Instrument.class, InstrumentDto.class)
                .addMappings(mapper -> mapper.using(instrumentHistory).map(Instrument::getIdd, InstrumentDto::setHistory))
                .addMappings(mapper -> mapper.using(roomList).map(Instrument::getIdd, InstrumentDto::setRooms));

        //Создание зависимости course и student
        Converter<Integer, List<StudentHistoryDto>> studentHistory =
                context -> mapList(studentDao.getHistory(context.getSource()), StudentHistoryDto.class);
        Converter<Integer, List<CourseListDto>> courseList =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseListDto.class);

        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(studentHistory).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(courseList).map(Student::getIdd, StudentDto::setCourses));

        Converter<Integer, List<CourseHistoryDto>> courseHistory =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseHistoryDto.class);
        Converter<Integer, List<StudentListDto>> studentList =
                context -> mapList(studentDao.getHistory(context.getSource()), StudentListDto.class);

        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(courseHistory).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(studentList).map(Student::getIdd, StudentDto::setCourses));

        //Создание зависимости course и teacher
        Converter<Integer, List<TeacherHistoryDto>> teacherHistory =
                context -> mapList(teacherDao.getHistory(context.getSource()), TeacherHistoryDto.class);
        Converter<Integer, List<CourseListDto>> courseListT =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseListDto.class);

        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(teacherHistory).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(courseListT).map(Student::getIdd, StudentDto::setCourses));

        Converter<Integer, List<CourseHistoryDto>> courseHistoryT =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseHistoryDto.class);
        Converter<Integer, List<TeacherListDto>> teacherList =
                context -> mapList(teacherDao.getHistory(context.getSource()), TeacherListDto.class);

        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(courseHistoryT).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(teacherList).map(Student::getIdd, StudentDto::setCourses));
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
