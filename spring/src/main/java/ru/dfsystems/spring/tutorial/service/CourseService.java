package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.CourseListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToRoom;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.mapping.CourseMapper;
import ru.dfsystems.spring.tutorial.mapping.RoomMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
@AllArgsConstructor
public class CourseService {

    private CourseDaoImpl courseDao;
    private CourseListDao courseListDao;
    private StudentToCourseDao studentToCourseDao;

    public Page<CourseListDto> getCoursesByParams(PageParams<CourseParams> pageParams) {
        Page<Course> page = courseListDao.list(pageParams);
        List<CourseListDto> list = CourseMapper.COURSE_MAPPER.courseListToCourseListDto(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(CourseDto courseDto) {
        courseDao.create(CourseMapper.COURSE_MAPPER.courseDtoToCourse(courseDto));
    }

    public CourseDto get(Integer idd) {
        return CourseMapper.COURSE_MAPPER.courseToCourseDto(courseDao.getActiveByIdd(idd));
    }

    @Transactional
    public void delete(Integer idd) {
        Course course = courseDao.getActiveByIdd(idd);
        course.setDeleteDate(LocalDateTime.now());
        courseDao.update(course);
    }

    @Transactional
    public void putStudent(Integer idd, Integer StudentIdd) {
        StudentToCourse link = new StudentToCourse();
        link.setCourseIdd(idd);
        link.setStudentIdd(StudentIdd);
        studentToCourseDao.insert(link);
    }

    @Transactional
    public CourseDto update(Integer idd, CourseDto courseDto) {
        Course course = courseDao.getActiveByIdd(idd);
        if (course == null){
            throw new RuntimeException("");
        }
        course.setDeleteDate(LocalDateTime.now());
        courseDao.update(course);

        Course newCourse = CourseMapper.COURSE_MAPPER.courseDtoToCourse(courseDto);
        newCourse.setIdd(course.getIdd());
        courseDao.create(newCourse);
        return CourseMapper.COURSE_MAPPER.courseToCourseDto(newCourse);
    }
}
