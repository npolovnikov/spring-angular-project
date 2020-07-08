package ru.dfsystems.spring.origin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.CourseDaoImpl;
import ru.dfsystems.spring.origin.dao.StudentDaoImpl;
import ru.dfsystems.spring.origin.dto.Course.CourseDto;
import ru.dfsystems.spring.origin.dto.Course.CourseListDto;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.student.StudentDto;
import ru.dfsystems.spring.origin.dto.student.StudentHistoryDto;
import ru.dfsystems.spring.origin.dto.student.StudentListDto;
import ru.dfsystems.spring.origin.dto.student.StudentParams;
import ru.dfsystems.spring.origin.generated.tables.daos.StudentToCourseDao;
import ru.dfsystems.spring.origin.generated.tables.pojos.Student;
import ru.dfsystems.spring.origin.generated.tables.pojos.StudentToCourse;
import ru.dfsystems.spring.origin.listDao.StudentListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentDaoImpl studentDao;
    private StudentListDao studentListDao;
    private MappingService mappingService;
    private CourseDaoImpl courseDao;
    private StudentToCourseDao studentToCourseDao;

    public Page<StudentListDto> getStudentByParams(PageParams<StudentParams> pageParams) {
        Page<Student> page = studentListDao.list(pageParams);
        List<StudentListDto> list = mappingService.mapList(page.getList(), StudentListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(StudentDto studentDto){
        studentDao.create(mappingService.map(studentDto, Student.class));
    }

    public StudentDto get(Integer idd){
        StudentDto dto = mappingService.map(studentDao.getActiveByIdd(idd), StudentDto.class);
        dto.setHistory(mappingService.mapList(studentDao.getHistory(idd), StudentHistoryDto.class));
        dto.setCourse(mappingService.mapList(courseDao.getCourseByStudentIdd(idd), CourseListDto.class));
        return dto;
    }
    public List<CourseListDto> getCourse(Integer idd){
        return mappingService.mapList(courseDao.getCourseByStudentIdd(idd), CourseListDto.class);
    }
    public List<StudentHistoryDto> getHistory(Integer idd){
        return mappingService.mapList(studentDao.getHistory(idd), StudentHistoryDto.class);
    }

    public void delete(Integer idd) {
        Student student = studentDao.getActiveByIdd(idd);
        student.setDeleteDate(LocalDateTime.now());
        studentDao.update(student);
    }

    public void putCourse(Integer idd, Integer courseIdd){
        StudentToCourse link = new StudentToCourse();
        link.setCourseIdd(idd);
        link.setStudentIdd(courseIdd);
        studentToCourseDao.insert(link);
    }


    public StudentDto update(Integer idd, StudentDto studentDto){
        Student student = studentDao.getActiveByIdd(idd);
        if(student == null){
            throw new RuntimeException("");
        }
        student.setDeleteDate(LocalDateTime.now());

        studentDao.update(student);
        Student newStudent = mappingService.map(studentDto, Student.class);
        newStudent.setIdd(student.getIdd());
        studentDao.create(newStudent);
        return mappingService.map(student, StudentDto.class);
    }

}
