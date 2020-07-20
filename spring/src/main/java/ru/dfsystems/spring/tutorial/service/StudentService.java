package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.CourseToStudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseList;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService extends BaseService<StudentDto, StudentDto, StudentParams, Student>{

    private CourseToStudentDaoImpl courseToStudentDao;

    @Autowired
    public StudentService(StudentDaoImpl studentListDao, StudentDaoImpl studentDao, MappingService mappingService, CourseToStudentDaoImpl courseToStudentDao) {
        super(mappingService, studentListDao, studentDao, StudentDto.class, StudentDto.class, Student.class);
        this.courseToStudentDao = courseToStudentDao;
    }

    public StudentDto create(StudentDto dto) {
        StudentDto result = super.create(dto, null);
        mergeInstruments(dto.getIdd(), dto.getCourses(), result.getCourses());
        return get(result.getIdd());
    }

    public StudentDto update(Integer idd, StudentDto dto) {
        StudentDto result = super.update(idd, dto, null);
        mergeInstruments(dto.getIdd(), dto.getCourses(), result.getCourses());
        return get(result.getIdd());
    }

    private void mergeInstruments(Integer roomIdd, List<CourseList> newInstruments, List<CourseList> oldInstruments) {
        List<Integer> newIdds = newInstruments.stream().map(BaseListDto::getIdd).collect(Collectors.toList());
        List<Integer> oldIdds = oldInstruments.stream().map(BaseListDto::getIdd).collect(Collectors.toList());

        List<Integer> iddsToBeDelete = oldIdds.stream().filter(o -> !newIdds.contains(o)).collect(Collectors.toList());
        List<Integer> iddsToBeAdd = newIdds.stream().filter(o -> !oldIdds.contains(o)).collect(Collectors.toList());

        courseToStudentDao.deleteByStudentAndCourseIdd(roomIdd, iddsToBeDelete);
        courseToStudentDao.createByStudentAndCourseIdd(roomIdd, iddsToBeAdd);
    }
}
