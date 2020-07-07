package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.CourseListDao;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.mapping.BaseMapper;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {

    private StudentToCourseDao studentToCourseDao;

    @Autowired
    public CourseService(BaseMapper<Course, CourseDto, CourseListDto> baseMapper,
                         BaseListDao<Course, CourseParams> listDao, BaseDao<Course> baseDao,
                         StudentToCourseDao studentToCourseDao) {
        super(baseMapper, listDao, baseDao);
        this.studentToCourseDao = studentToCourseDao;
    }

    @Transactional
    public void putStudent(Integer idd, Integer StudentIdd) {
        StudentToCourse link = new StudentToCourse();
        link.setCourseIdd(idd);
        link.setStudentIdd(StudentIdd);
        studentToCourseDao.insert(link);
    }

}
