package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.CourseListDao;
import ru.dfsystems.spring.tutorial.dao.standard.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {

    private CourseDaoImpl courseDao;

    public CourseService(CourseListDao courseListDao, CourseDaoImpl courseDao, MappingService mappingService) {
        super(mappingService, courseListDao, courseDao, CourseListDto.class, CourseDto.class, Course.class);
        this.courseDao = courseDao;
    }

    @Transactional
    public List<Lesson> getLessonsByIdd(Integer idd) {
        return courseDao.getLessonsByIdd(idd);
    }
}