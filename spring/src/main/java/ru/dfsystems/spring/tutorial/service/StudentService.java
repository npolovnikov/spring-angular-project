package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class StudentService extends BaseService<StudentListDto, StudentDto, StudentParams, Student> {
    StudentToCourseDao studentToCourseDao;
    CourseDaoImpl courseDao;
    MappingService mappingService;

    @Autowired
    public StudentService(StudentListDao studentListDao,
                          StudentDaoImpl studentDao,
                          StudentToCourseDao studentToCourseDao,
                          CourseDaoImpl courseDao,
                          MappingService mappingService) {
        super(mappingService, studentListDao, studentDao, StudentListDto.class, StudentDto.class, Student.class);
        this.studentToCourseDao = studentToCourseDao;
        this.courseDao = courseDao;
        this.mappingService = mappingService;
    }

    /**
     * Запись сутдента на новый курс, для этого добавялем запись в таблицу StudentToCourse
     */
    @Transactional
    public void putCourse(Integer idd, Integer courseIdd) {
        StudentToCourse link = new StudentToCourse();
        link.setStudentIdd(idd);
        link.setCourseIdd(courseIdd);
        studentToCourseDao.insert(link);
    }

    @Transactional
    public Page<CourseListDto> getCoursesByStudentIdd(Integer idd) {
        List<CourseListDto> list = mappingService.mapList(courseDao.getCoursesByStudentIdd(idd), CourseListDto.class);
        return new Page<>(list, (long) list.size()); //?
    }
}
