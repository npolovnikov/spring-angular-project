package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.StudentListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.mapping.RoomMapper;
import ru.dfsystems.spring.tutorial.mapping.StudentMapper;
import ru.dfsystems.spring.tutorial.mapping.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
@AllArgsConstructor
public class StudentService {

    private StudentDaoImpl studentDao;
    private StudentListDao studentListDao;

    public Page<StudentListDto> getStudentsByParams(PageParams<StudentParams> pageParams) {
        Page<Student> page = studentListDao.list(pageParams);
        List<StudentListDto> list = StudentMapper.STUDENT_MAPPER.studentListToStudentListDto(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(StudentDto studentDto) {
        studentDao.create(StudentMapper.STUDENT_MAPPER.studentDtoToStudent(studentDto));
    }

    public StudentDto get(Integer idd) {
        return StudentMapper.STUDENT_MAPPER.studentToStudentDto(studentDao.getActiveByIdd(idd));
    }

    @Transactional
    public void delete(Integer idd) {
        Student student = studentDao.getActiveByIdd(idd);
        student.setDeleteDate(LocalDateTime.now());
        studentDao.update(student);
    }

    public StudentDto update(Integer idd, StudentDto studentDto) {
        Student student = studentDao.getActiveByIdd(idd);
        if (student == null) {
            throw new RuntimeException("ошибка");
        }
        student.setDeleteDate(LocalDateTime.now());
        studentDao.update(student);

        Student newStudent = StudentMapper.STUDENT_MAPPER.studentDtoToStudent(studentDto);
        newStudent.setIdd(student.getIdd());
        studentDao.create(newStudent);
        return StudentMapper.STUDENT_MAPPER.studentToStudentDto(newStudent);
    }
}
