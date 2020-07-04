package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.StudentDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentDaoImpl studentDao;

    public List<Students> getAllStudents() {
        studentDao.fetchOneById(12);
        studentDao.getActiveByIdd(12);
        return studentDao.findAll();
    }
    public Page<Students> getStudentsByParam(PageParams<StudentParams> pageParams) {
        return studentDao.getStudentsByParam(pageParams);
    }
}
