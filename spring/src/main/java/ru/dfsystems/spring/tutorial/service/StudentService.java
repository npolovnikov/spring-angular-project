package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.student.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentListDao;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class StudentService extends BaseService<StudentListDto, StudentDto, StudentParams, Student> {

    private StudentDao studentDao;

    @Autowired
    public StudentService(StudentListDao studentListDao, StudentDaoImpl studentDaoImpl,
                          StudentDao studentDao, MappingService mappingService) {
        super(mappingService, studentListDao, studentDaoImpl, StudentListDto.class,
                StudentDto.class, Student.class);
        this.studentDao = studentDao;
    }


    @Transactional
    public void putCourse(Integer studentIdd, Integer courseIdd) {
        List<Student> studentList = studentDao.findAll();
        int maxId = 0;
        Student student = new Student();
        for (Student studentItr : studentList) {
            if (studentItr.getIdd().equals(studentIdd) && studentItr.getId() >= maxId) {
                maxId = studentItr.getId();
                student = studentItr;
            }
        }
        student.setCourseIdd(courseIdd);
        studentDao.update(student);
    }
}
