package ru.student.studentSpring.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.StudentDaoImpl;
import ru.student.studentSpring.tutorial.dao.StudentListDao;
import ru.student.studentSpring.tutorial.dto.student.StudentDto;
import ru.student.studentSpring.tutorial.dto.student.StudentHistoryDto;
import ru.student.studentSpring.tutorial.dto.student.StudentListDto;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.generated.tables.daos.StudentToCoursesDao;
import ru.student.studentSpring.tutorial.generated.tables.pojos.StudentToCourses;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class StudentService extends BaseService<StudentListDto, StudentDto, StudentParams, Students> {

    private StudentDaoImpl studentDao;
    private StudentToCoursesDao studentToCourses;
    private MappingService mappingService;

    public StudentService(StudentListDao studentListDao, StudentDaoImpl studentDao,
                          StudentToCoursesDao studentToCourses,
                          MappingService mappingService) {
        super(mappingService, studentListDao, StudentListDto.class, studentDao, StudentDto.class, Students.class);
        this.studentToCourses = studentToCourses;
    }

    public List<StudentHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(studentDao.getHistory(idd), StudentHistoryDto.class);
    }

    @Transactional
    public void putCourse(Integer idd, Integer courseId) {
        StudentToCourses link = new StudentToCourses();
        link.setStudentIdd(idd);
        link.setCourseIdd(courseId);
        studentToCourses.insert(link);
    }

}
