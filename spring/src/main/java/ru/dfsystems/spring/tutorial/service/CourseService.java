package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseList;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.mapping.MappingService;


@Service
public class CourseService  extends BaseService<CourseList, CourseDto, CourseParams, Course>{

    @Autowired
    public CourseService(CourseDaoImpl courseListDao, CourseDaoImpl courseDao, MappingService mappingService) {
        super(mappingService, courseListDao, courseDao, CourseList.class, CourseDto.class, Course.class);
    }
}
