package ru.student.studentSpring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.*;
import ru.student.studentSpring.tutorial.dto.course.CourseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseHistoryDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.student.StudentDto;
import ru.student.studentSpring.tutorial.dto.student.StudentHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;
import ru.student.studentSpring.tutorial.generated.tables.pojos.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingService implements BaseMapping {
    private final ModelMapper modelMapper;
    private final RoomDaoImpl roomDao;
    private final InstrumentDaoImpl instrumentDao;
    private final CourseDaoImpl courseDao;
    private final TeacherDaoImpl teacherDao;
    private final StudentDaoImpl studentDao;

    @PostConstruct
    public void init() {
        Converter<Integer, List<RoomHistoryDto>> roomHistory =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomHistoryDto.class);
        Converter<Integer, List<InstrumentListDto>> instrumentList =
                context -> mapList(instrumentDao.getInstrumentsByRoom(context.getSource()), InstrumentListDto.class);

         modelMapper.typeMap(Rooms.class, RoomDto.class)
                .addMappings(mapper -> mapper.using(roomHistory).map(Rooms::getIdd, RoomDto::setHistory))
                .addMappings(mapper -> mapper.using(instrumentList).map(Rooms::getIdd, RoomDto::setInstruments));


        Converter<Integer, List<CourseHistoryDto>> courseHistory =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseHistoryDto.class);
        Converter<Integer, List<TeacherListDto>> teacherList =
                context -> mapList(teacherDao.getTeachers(context.getSource()), TeacherListDto.class);

        modelMapper.typeMap(Courses.class, CourseDto.class)
                .addMappings(mapper -> mapper.using(courseHistory).map(Courses::getIdd, CourseDto::setHistory))
                .addMappings(mapper -> mapper.using(teacherList).map(Courses::getIdd, CourseDto::setTeacher));


        Converter<Integer, List<InstrumentHistoryDto>> instrumentHistory =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentHistoryDto.class);

        modelMapper.typeMap(Instruments.class, InstrumentDto.class)
                .addMappings(mapper -> mapper.using(instrumentHistory).map(Instruments::getIdd, InstrumentDto::setHistory));


    Converter<Integer, List<StudentHistoryDto>> studentHistory =
            context -> mapList(studentDao.getHistory(context.getSource()), StudentHistoryDto.class);
    Converter<Integer, List<CourseListDto>> courseList =
            context -> mapList(courseDao.getCourses(context.getSource()), CourseListDto.class);

        modelMapper.typeMap(Students.class, StudentDto.class)
            .addMappings(mapper -> mapper.using(studentHistory).map(Students::getIdd, StudentDto::setHistory))
            .addMappings(mapper -> mapper.using(courseList).map(Students::getIdd, StudentDto::setCourses));

        Converter<Integer, List<TeacherHistoryDto>> teacherHistory =
                context -> mapList(teacherDao.getHistory(context.getSource()), TeacherHistoryDto.class);

        modelMapper.typeMap(Teachers.class, TeacherDto.class)
                .addMappings(mapper -> mapper.using(teacherHistory).map(Teachers::getIdd, TeacherDto::setHistory));

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
