package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.*;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.dto.student.StudentDto;
import ru.student.studentSpring.tutorial.dto.student.StudentHistoryDto;
import ru.student.studentSpring.tutorial.dto.student.StudentListDto;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.daos.InstrumentsToRoomsDao;
import ru.student.studentSpring.tutorial.generated.tables.daos.StudentToCoursesDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.*;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentListDao studentListDao;
    private final StudentDaoImpl studentDao;
    private final CourseDaoImpl courseDao;
    private final StudentToCoursesDao studentToCourses;
    private final MappingService mappingService;

    public Page<StudentListDto> getStudentsByParam(PageParams<StudentParams> pageParams) {
        Page<Students> page = studentListDao.list(pageParams);
        List<StudentListDto> list = mappingService.mapList(page.getList(), StudentListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(StudentDto studentDto) {
        studentDao.create(mappingService.map(studentDto, Students.class));
    }

    public StudentDto get(Integer idd) {
        StudentDto dto = mappingService.map(studentDao.getActiveByIdd(idd), StudentDto.class);
        dto.setHistory(getHistory(idd));
        dto.setCourses(mappingService.mapList(courseDao.getCoursesByStudent(idd), CourseListDto.class));
        return dto;
    }

    public List<StudentHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(studentDao.getHistory(idd), StudentHistoryDto.class);
    }

    @Transactional
    public void delete(Integer idd) {
        Students student = studentDao.getActiveByIdd(idd);
        student.setDeleteDate(LocalDateTime.now());
        studentDao.update(student);
    }

    @Transactional
    public void putCourse(Integer idd, Integer courseId) {
        StudentToCourses link = new StudentToCourses();
        link.setStudentIdd(idd);
        link.setCourseIdd(courseId);
        studentToCourses.insert(link);
    }

    @Transactional
    public StudentDto update(Integer idd, StudentDto studentDto) {
        Students student = studentDao.getActiveByIdd(idd);
        if (student == null) {
            throw new RuntimeException("");
        }
        student.setDeleteDate(LocalDateTime.now());
        studentDao.update(student);
        Students newStudent = mappingService.map(studentDto, Students.class);
        newStudent.setIdd(student.getIdd());
        studentDao.create(newStudent);
        return mappingService.map(student, StudentDto.class);
    }
}
