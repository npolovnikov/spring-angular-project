package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentDaoImpl studentDao;

    public Page<Student> getStudentsByParams(PageParams<StudentParams> pageParams) {
        return studentDao.getStudentsByParams(pageParams);
    }
}