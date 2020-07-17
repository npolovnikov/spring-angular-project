package ru.dfsystems.spring.tutorial.mapping;

import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.istrument.InstrumentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.room.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherDaoImpl;
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
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.*;

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
    private TeacherDaoImpl teacherDao;
    private LessonDaoImpl lessonDao;


    @PostConstruct
    public void init() {
        //ROOM
        Converter<Integer, List<RoomHistoryDto>> roomHistory =
                context -> mapList(roomDao.getHistory(context.getSource()), RoomHistoryDto.class);
        Converter<Integer, List<InstrumentListDto>> instrumentList =
                context -> mapList(instrumentDao.getInstrumentsByRoomIdd(context.getSource()), InstrumentListDto.class);
        modelMapper.typeMap(Room.class, RoomDto.class)
                .addMappings(mapper -> mapper.using(roomHistory).map(Room::getIdd, RoomDto::setHistory))
                .addMappings(mapper -> mapper.using(instrumentList).map(Room::getIdd, RoomDto::setInstruments));

        //Instrument
        Converter<Integer, List<InstrumentHistoryDto>> instrumentHistory =
                context -> mapList(instrumentDao.getHistory(context.getSource()), InstrumentHistoryDto.class);
        modelMapper.typeMap(Instrument.class, InstrumentDto.class)
                .addMappings(mapper -> mapper.using(instrumentHistory).map(Instrument::getIdd,InstrumentDto::setHistory));

        //Student
        Converter<Integer, List<StudentHistoryDto>> studentHistory =
                context -> mapList(studentDao.getHistory(context.getSource()), StudentHistoryDto.class);
        Converter<Integer, List<CourseListDto>> studentCourseList =
                context -> mapList(courseDao.getCoursesByStudentIdd(context.getSource()), CourseListDto.class);
        modelMapper.typeMap(Student.class, StudentDto.class)
                .addMappings(mapper -> mapper.using(studentHistory).map(Student::getIdd, StudentDto::setHistory))
                .addMappings(mapper -> mapper.using(studentCourseList).map(Student::getIdd, StudentDto::setCourses));

        //Teacher
        Converter<Integer, List<TeacherHistoryDto>> teacherHistory =
                context -> mapList(teacherDao.getHistory(context.getSource()), TeacherHistoryDto.class);
        Converter<Integer, List<CourseListDto>> teacherCourseList =
                context -> mapList(courseDao.getCoursesByTeacherIdd(context.getSource()), CourseListDto.class);
        modelMapper.typeMap(Teacher.class, TeacherDto.class)
                .addMappings(mapper -> mapper.using(teacherHistory).map(Teacher::getIdd,TeacherDto::setHistory))
                .addMappings(mapper -> mapper.using(teacherCourseList).map(Teacher::getIdd, TeacherDto::setCourses));

        //Course
        Converter<Integer, List<CourseHistoryDto>> courseHistory =
                context -> mapList(courseDao.getHistory(context.getSource()), CourseHistoryDto.class);
        Converter<Integer, List<StudentListDto>> studentList =
                context -> mapList(studentDao.getStudentsByCourseIdd(context.getSource()), StudentListDto.class);
        Converter<Integer, List<LessonListDto>> lessonList =
                context -> mapList(lessonDao.getLessonsByCourseIdd(context.getSource()), LessonListDto.class);
        modelMapper.typeMap(Course.class, CourseDto.class)
                .addMappings(mapper -> mapper.using(courseHistory).map(Course::getIdd,CourseDto::setHistory))
                .addMappings(mapper -> mapper.using(studentList).map(Course::getIdd, CourseDto::setStudents))
                .addMappings(mapper -> mapper.using(lessonList).map(Course::getIdd, CourseDto::setLessons));

        //lesson
        Converter<Integer, List<LessonHistoryDto>> lessonHistory =
                context -> mapList(lessonDao.getHistory(context.getSource()), LessonHistoryDto.class);

        modelMapper.typeMap(Lesson.class, LessonDto.class)
                .addMappings(mapper -> mapper.using(lessonHistory).map(Lesson::getIdd,LessonDto::setHistory));

        //Дополнительные настройки.
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
