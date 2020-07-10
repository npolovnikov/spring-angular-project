package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentListDao;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class StudentService extends BaseService<StudentListDto, StudentDto, StudentParams, Student> {

    @Autowired
    public StudentService(StudentListDao instrumentListDao, StudentDaoImpl instrumentDao, MappingService mappingService) {
        super(mappingService, instrumentListDao, instrumentDao, StudentListDto.class, StudentDto.class, Student.class);
    }
}
