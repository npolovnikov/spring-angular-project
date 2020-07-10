package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.Course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.Course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.listDao.CourseListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, ru.dfsystems.spring.tutorial.generated.tables.pojos.Course>{
    private CourseDaoImpl courseDao;
    private CourseListDao courseListDao;
    private MappingService mappingService;

    @Autowired
    public CourseService(CourseDaoImpl courseDao, CourseListDao courseListDao, MappingService mappingService) {
        super(mappingService, courseListDao, courseDao, CourseListDto.class, CourseDto.class, Course.class);
        this.courseDao = courseDao;
        this.courseListDao = courseListDao;
        this.mappingService = mappingService;
    }
}
