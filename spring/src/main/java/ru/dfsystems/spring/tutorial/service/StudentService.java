package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.listDao.StudentListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class StudentService  extends BaseService<StudentListDto, StudentDto, StudentParams, Student>{

    private StudentDaoImpl studentDao;
    private StudentListDao studentListDao;
    private MappingService mappingService;
    private CourseDaoImpl courseDao;
    private StudentToCourseDao studentToCourseDao;

    @Autowired
    public StudentService(StudentDaoImpl studentDao, StudentListDao studentListDao, MappingService mappingService, CourseDaoImpl courseDao, StudentToCourseDao studentToCourseDao) {
        super(mappingService, studentListDao, studentDao, StudentListDto.class, StudentDto.class, Student.class);
        this.studentDao = studentDao;
        this.studentListDao = studentListDao;
        this.mappingService = mappingService;
        this.courseDao = courseDao;
        this.studentToCourseDao = studentToCourseDao;
    }

    @Transactional
    public List<CourseListDto> getCourse(Integer idd){
        return mappingService.mapList(courseDao.getCourseByStudentIdd(idd), CourseListDto.class);
    }
    @Transactional
    public void putCourse(Integer idd, Integer courseIdd){
        StudentToCourse link = new StudentToCourse();
        link.setCourseIdd(idd);
        link.setStudentIdd(courseIdd);
        studentToCourseDao.insert(link);
    }
}
