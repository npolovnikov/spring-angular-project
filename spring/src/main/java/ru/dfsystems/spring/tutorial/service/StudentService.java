package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentListDao;
import ru.dfsystems.spring.tutorial.dao.StudentToCourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService extends BaseService<StudentListDto, StudentDto, StudentParams, Student> {

    private StudentToCourseDaoImpl studentToCourseDao;

    @Autowired
    public StudentService(StudentListDao studentListDao, StudentDaoImpl studentDao, StudentToCourseDaoImpl studentToCourseDao, MappingService mappingService) {
        super(mappingService, studentListDao, studentDao, StudentListDto.class, StudentDto.class, Student.class);
        this.studentToCourseDao = studentToCourseDao;
    }

    @Transactional
    public void putCourse(Integer idd, Integer courseIdd) {
        StudentToCourse link = new StudentToCourse();
        link.setStudentIdd(idd);
        link.setCourseIdd(courseIdd);
        studentToCourseDao.insert(link);
    }
    
    @Override
    public StudentDto create(StudentDto dto) {
        StudentDto result = super.create(dto);
        mergeCourses(dto.getIdd(), dto.getCourses(), result.getCourses());
        return get(result.getIdd());
    }

    @Override
    public StudentDto update(Integer idd, StudentDto dto) {
        StudentDto result = super.update(idd, dto);
        mergeCourses(dto.getIdd(), dto.getCourses(), result.getCourses());
        return get(result.getIdd());
    }

    private void mergeCourses(Integer studentIdd, List<CourseListDto> newCourses, List<CourseListDto> oldCourses) {
        if (newCourses == null) {
            return;
        }
        List<Integer> newIdds = newCourses.stream().map(BaseListDto::getIdd).collect(Collectors.toList());
        List<Integer> oldIdds = oldCourses.stream().map(BaseListDto::getIdd).collect(Collectors.toList());

        List<Integer> iddsToBeDelete = oldIdds.stream().filter(o -> !newIdds.contains(o)).collect(Collectors.toList());
        List<Integer> iddsToBeAdd = newIdds.stream().filter(o -> !oldIdds.contains(o)).collect(Collectors.toList());

        studentToCourseDao.deleteByStudentAndCourseIdd(studentIdd, iddsToBeDelete);
        studentToCourseDao.createByStudentAndCourseIdd(studentIdd, iddsToBeAdd);
    }
}
