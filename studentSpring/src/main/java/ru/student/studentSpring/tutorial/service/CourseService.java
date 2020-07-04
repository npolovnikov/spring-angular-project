package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.CourseDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseDaoImpl courseDao;

    public List<Courses> getAllCourses() {
        courseDao.fetchOneById(12);
        courseDao.getActiveByIdd(12);
        return courseDao.findAll();
    }
    public Page<Courses> getCoursesByParam(PageParams<CoursesParams> pageParams) {
        return courseDao.getCoursesByParam(pageParams);
    }
}
