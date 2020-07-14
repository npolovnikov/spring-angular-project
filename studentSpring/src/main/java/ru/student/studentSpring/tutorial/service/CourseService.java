package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.CourseDaoImpl;
import ru.student.studentSpring.tutorial.dao.CourseListDao;
import ru.student.studentSpring.tutorial.dao.TeacherDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CourseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseHistoryDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;
import ru.student.studentSpring.tutorial.generated.tables.pojos.InstrumentsToRooms;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseListDao courseListDao;
    private final CourseDaoImpl courseDao;
    private final MappingService mappingService;
    private final TeacherDaoImpl teacherDao;

    public Page<CourseListDto> getCourseByParam(PageParams<CoursesParams> pageParams) {
        Page<Courses> page = courseListDao.list(pageParams);
        List<CourseListDto> list = mappingService.mapList(page.getList(), CourseListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(CourseDto courseDto) {
        courseDao.create(mappingService.map(courseDto, Courses.class));
    }

    public CourseDto get(Integer idd) {
        CourseDto dto = mappingService.map(courseDao.getActiveByIdd(idd), CourseDto.class);
        dto.setHistory(getHistory(idd));
        dto.setTeacher(mappingService.mapList(teacherDao.getTeachers(idd), TeacherListDto.class));
        return dto;
    }

    public List<CourseHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(courseDao.getHistory(idd), CourseHistoryDto.class);
    }

    @Transactional
    public void delete(Integer idd) {
        Courses course = courseDao.getActiveByIdd(idd);
        course.setDeleteDate(LocalDateTime.now());
        courseDao.update(course);
    }


    @Transactional
    public CourseDto update(Integer idd, CourseDto courseDto) {
        Courses course = courseDao.getActiveByIdd(idd);
        if (course == null) {
            throw new RuntimeException("");
        }
        course.setDeleteDate(LocalDateTime.now());
        courseDao.update(course);
        Courses newCourse = mappingService.map(courseDto, Courses.class);
        newCourse.setIdd(course.getIdd());
        courseDao.create(newCourse);
        return mappingService.map(course, CourseDto.class);
    }

}
