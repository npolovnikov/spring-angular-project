package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {

    private CourseDao courseDao;

    @Autowired
    public CourseService(CourseListDao courseListDao, CourseDaoImpl courseDaoImpl, CourseDao courseDao, MappingService mappingService) {
        super(mappingService, courseListDao, courseDaoImpl, CourseListDto.class, CourseDto.class, Course.class);
        this.courseDao = courseDao;
    }

    @Transactional
    public void putTeacher(Integer idd, Course course) {
        course.setTeacherIdd(idd);
        courseDao.update(course);
    }

}
