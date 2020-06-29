package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseDaoImpl courseDao;

    public List<Course> getAllCourses() {
        courseDao.fetchOneById(152);
        courseDao.getActiveByIdd(152);
        return courseDao.findAll();
    }

    public Page<Course> getCoursesByParams(PageParams<CourseParams> pageParams) {
        return courseDao.getCoursesByParams(pageParams);
    }

}