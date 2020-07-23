package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentListDao;
import ru.dfsystems.spring.tutorial.dao.StudentsToCourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.StudentsToCourse;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService  extends BaseService<StudentListDto, StudentDto, StudentParams, Student> {
    private final CourseDaoImpl courseDao;
    private final StudentsToCourseDaoImpl studentToCourseDao;
    private final MappingService mappingService;

    @Autowired
    public StudentService(MappingService mappingService, StudentListDao studentListDao, StudentDaoImpl studentDao, CourseDaoImpl courseDao, StudentsToCourseDaoImpl studentToCourseDao) {
        super(mappingService, studentListDao, studentDao, StudentListDto.class, StudentDto.class, Student.class);
        this.courseDao = courseDao;
        this.mappingService = mappingService;
        this.studentToCourseDao = studentToCourseDao;
    }

    public List<CourseListDto> getCourses(Integer studentIdd) {
        return mappingService.mapList(courseDao.getCoursesByStudentIdd(studentIdd), CourseListDto.class);
    }

    @Transactional
    public void addInCourse(Integer idd, Integer courseIdd) {
        var link = new StudentsToCourse();
        link.setStudentIdd(idd);
        link.setCourseIdd(courseIdd);
        studentToCourseDao.insert(link);
    }

    @Override
    public StudentDto create(StudentDto dto) {
        return super.create(dto);
    }

    @Override
    public StudentDto update(Integer idd, StudentDto dto) {
        return super.update(idd, dto);
    }

    private void mergeCourses(Integer courseIdd, List<CourseListDto> newCourses, List<CourseListDto> oldCourses) {
        List<Integer> newIdds = newCourses.stream().map(BaseListDto::getIdd).collect(Collectors.toList());
        List<Integer> oldIdds = oldCourses.stream().map(BaseListDto::getIdd).collect(Collectors.toList());

        List<Integer> iddsToBeDelete = oldIdds.stream().filter(o -> !newIdds.contains(o)).collect(Collectors.toList());
        List<Integer> iddsToBeAdd = newIdds.stream().filter(o -> !oldIdds.contains(o)).collect(Collectors.toList());


        studentToCourseDao.deleteByStudentAndCourseIdd(courseIdd, iddsToBeDelete);
        studentToCourseDao.createByStudentAndCourseIdd(courseIdd, iddsToBeAdd);
    }
}
