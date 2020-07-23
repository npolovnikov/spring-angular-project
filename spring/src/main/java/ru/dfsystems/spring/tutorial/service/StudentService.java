package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentListDao;
import ru.dfsystems.spring.tutorial.dao.student_to_course.StudentToCourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
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
    CourseDaoImpl courseDao;
    MappingService mappingService;

    @Autowired
    public StudentService(StudentListDao studentListDao,
                          StudentDaoImpl studentDao,
                          StudentToCourseDaoImpl studentToCourseDao,
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

    /**
     * Добавляет и удаляет инструменты при создании и реадкировании румдто
     */
    private void mergeCourses(Integer studentIdd, List<CourseListDto> newCourses, List<CourseListDto> oldCourses) {
        List<Integer> newIdds = newCourses.stream().map(BaseListDto::getIdd).collect(Collectors.toList());
        List<Integer> oldIdds = oldCourses.stream().map(BaseListDto::getIdd).collect(Collectors.toList());

        List<Integer> iddsToBeDelete = oldIdds.stream().filter(o -> !newIdds.contains(o)).collect(Collectors.toList());
        List<Integer> iddsToBeAdd = newIdds.stream().filter(o -> !oldIdds.contains(o)).collect(Collectors.toList());

        studentToCourseDao.deleteByStudentAndCourseIdd(studentIdd, iddsToBeDelete);
        studentToCourseDao.createByStudentAndCourseIdd(studentIdd, iddsToBeAdd);
    }
}
