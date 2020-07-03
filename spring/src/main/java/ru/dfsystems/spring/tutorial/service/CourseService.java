package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.ListDao.CourseListDao;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.LessonToCourse;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service

public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course>{
    private StudentToCourseDao studentToCourseDao;
    private LessonToCourseDao lessonToCourseDao;
    private BaseDao<Course> courseDao;
    @Autowired
    public CourseService(CourseListDao courseListDao, BaseDao<Course> courseDao, MappingService mappingService
            , StudentToCourseDao studentToCourseDao, LessonToCourseDao lessonToCourseDao) {
        super(mappingService, courseListDao, courseDao, CourseListDto.class, CourseDto.class, Course.class);
        this.studentToCourseDao = studentToCourseDao;
        this.lessonToCourseDao = lessonToCourseDao;
        this.courseDao = courseDao;
    }


    @Transactional
    public void putStudent(Integer idd, Integer studentIdd) {
        StudentToCourse link = new StudentToCourse();
        link.setCourseIdd(idd);
        link.setStudentIdd(studentIdd);
        studentToCourseDao.insert(link);
    }

    @Transactional
    public void putLesson(Integer idd, Integer lessonIdd) {
        LessonToCourse link = new LessonToCourse();
        link.setCourseIdd(idd);
        link.setLessonIdd(lessonIdd);
        lessonToCourseDao.insert(link);
    }

    @Transactional
    public void putTeacher(Integer idd, Integer teacherIdd) {
        Course course = courseDao.getActiveByIdd(idd);
        course.setTeacherIdd(teacherIdd);
        courseDao.update(course);
    }
}
