package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.course.CourseListDao;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.CourseDao;
//import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToLessonDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
//import ru.dfsystems.spring.tutorial.generated.tables.pojos.InstrumentToLesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {

    private CourseDao courseDao;

    //    private InstrumentToLessonDao instrumentToLessonDao;
//
//    @Autowired
//    public CourseService(CourseListDao courseListDao, CourseDaoImpl courseDaoImpl, CourseDao courseDao, MappingService mappingService, InstrumentToLessonDao instrumentToLessonDao) {
//        super(mappingService, courseListDao, courseDaoImpl, CourseListDto.class, CourseDto.class, Course.class);
//        this.courseDao = courseDao;
//        this.instrumentToLessonDao = instrumentToLessonDao;
//    }
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

//    @Transactional
//    public void putInstrument(Integer idd, Integer instrumentIdd) {
//        InstrumentToLesson link = new InstrumentToLesson();
//        link.setLessonId(idd);
//        link.setInstrumentIdd(instrumentIdd);
//        instrumentToLessonDao.insert(link);
//    }
}
