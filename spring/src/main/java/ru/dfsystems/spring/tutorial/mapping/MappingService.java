package ru.dfsystems.spring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.*;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonHistoryDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomHistoryDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MappingService implements BaseMapping {
    private ModelMapper modelMapper;
    private CourseDaoImpl courseDao;
    private InstrumentDaoImpl instrumentDao;
    private LessonDaoImpl lessonDao;
    private RoomDaoImpl roomDao;
    private StudentDaoImpl studentDao;
    private TeacherDaoImpl teacherDao;


    @PostConstruct
    public void init() {
        //Дополнительные настройки.
        //History
        Converter<Integer, List<CourseHistoryDto>> courseHistory =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseHistoryDto.class);
        Converter<Integer, List<InstrumentHistoryDto>> instrumentHistory =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentHistoryDto.class);
        Converter<Integer, List<LessonHistoryDto>> lessonHistory =
                context -> mapList(lessonDao.getHistory(context.getSource()), LessonHistoryDto.class);
        Converter<Integer, List<RoomHistoryDto>> roomHistory =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomHistoryDto.class);
        Converter<Integer, List<StudentHistoryDto>> studentHistory =
                context -> mapList(studentDao.getHistory(context.getSource()), StudentHistoryDto.class);
        Converter<Integer, List<TeacherHistoryDto>> teacherHistory =
                context -> mapList(teacherDao.getHistory(context.getSource()), TeacherHistoryDto.class);



        //Course
        Converter<Integer, List<StudentListDto>> courseStudentList =
                context -> mapList(studentDao.getStudentsByCourseIdd(context.getSource()), StudentListDto.class);
        Converter<Integer, List<LessonListDto>> courseLessonList =
                context -> mapList(lessonDao.getLessonByCourseIdd(context.getSource()), LessonListDto.class);
        Converter<Integer, TeacherDto> courseTeacher = mappingContext -> map(teacherDao.getActiveByIdd(mappingContext.getSource()), TeacherDto.class);

        //Instrument
        Converter<Integer, List<LessonListDto>> instrumentLessonList =
                context -> mapList(lessonDao.getLessonByInstrumentIdd(context.getSource()), LessonListDto.class);
        Converter<Integer, List<RoomListDto>> instrumentRoomList =
                context -> mapList(roomDao.getRoomByInstrumentIdd(context.getSource()), RoomListDto.class);

        //Lesson
        Converter<Integer, List<InstrumentListDto>> lessonInstrumentsList =
                context -> mapList(instrumentDao.getInstrumentsByLessonIdd(context.getSource()), InstrumentListDto.class);
        Converter<Integer, RoomDto> lessonRoom =
                mappingContext -> map(roomDao.getActiveByIdd(mappingContext.getSource()), RoomDto.class);
        Converter<Integer, CourseDto> lessonCourse =
                mappingContext -> map(courseDao.getActiveByIdd(mappingContext.getSource()), CourseDto.class);

        //Room
        Converter<Integer, List<InstrumentListDto>> roomInstrumentsList =
                context -> mapList(instrumentDao.getInstrumentsByRoomIdd(context.getSource()), InstrumentListDto.class);

        //Student
        Converter<Integer, List<CourseListDto>> studentCoursesList =
                context -> mapList(courseDao.getCoursesByStudentIdd(context.getSource()), CourseListDto.class);

        modelMapper.typeMap(Course.class, CourseDto.class)
                .addMappings(mapper -> mapper.using(courseHistory).map(Course::getIdd, CourseDto::setHistory))
                .addMappings(mapper -> mapper.using(courseStudentList).map(Course::getIdd, CourseDto::setStudents))
                .addMappings(mapper -> mapper.using(courseLessonList).map(Course::getIdd, CourseDto::setLessons))
                .addMappings(mapper -> mapper.using(courseTeacher).map(Course::getIdd, CourseDto::setTeacher));

        modelMapper.typeMap(Instrument.class, InstrumentDto.class)
                .addMappings(mapper -> mapper.using(instrumentHistory).map(Instrument::getIdd, InstrumentDto::setHistory))
                .addMappings(mapper -> mapper.using(instrumentLessonList).map(Instrument::getIdd, InstrumentDto::setLessons))
                .addMappings(mapper -> mapper.using(instrumentRoomList).map(Instrument::getIdd, InstrumentDto::setRooms));

        modelMapper.typeMap(Lesson.class, LessonDto.class)
                .addMappings(mapper -> mapper.using(lessonHistory).map(Lesson::getIdd, LessonDto::setHistory))
                .addMappings(mapper -> mapper.using(lessonInstrumentsList).map(Lesson::getIdd, LessonDto::setInstruments))
                .addMappings(mapping -> mapping.using(lessonCourse).map(Lesson::getCourseIdd, LessonDto::setCourse))
                .addMappings(mapping -> mapping.using(lessonRoom).map(Lesson::getRoomIdd, LessonDto::setRoom));


        modelMapper.typeMap(Room.class, RoomDto.class)
                .addMappings(mapper -> mapper.using(roomHistory).map(Room::getIdd, RoomDto::setHistory))
                .addMappings(mapper -> mapper.using(roomInstrumentsList).map(Room::getIdd, RoomDto::setInstruments));

        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(studentHistory).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(studentCoursesList).map(Student::getIdd, StudentDto::setCourses));

        modelMapper.typeMap(Teacher.class, TeacherDto.class)
                .addMappings(mapper -> mapper.using(teacherHistory).map(Teacher::getIdd, TeacherDto::setHistory));
    }

    public <S, D> D map(S source, Class<D> clazz) {
        return modelMapper.map(source, clazz);
    }

    public <S, D> void map(S source, D dest) {
        modelMapper.map(source, dest);
    }

    public <S, D> List<D> mapList(List<S> sources, Class<D> clazz){
        List<D> d =sources.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
        return sources.stream()
                .map(s -> map(s, clazz))
                .collect(Collectors.toList());
    }
}