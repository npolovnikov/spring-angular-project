package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.listDao.TeacherListDao;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class TeacherService  extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher>{

    private TeacherDaoImpl teacherDao;
    private MappingService mappingService;
    private TeacherListDao teacherListDao;
    private CourseDaoImpl courseDao;

    public TeacherService(TeacherDaoImpl teacherDao, MappingService mappingService, TeacherListDao teacherListDao, CourseDaoImpl courseDao) {
        super(mappingService,teacherListDao, teacherDao, TeacherListDto.class, TeacherDto.class, Teacher.class );
        this.teacherDao = teacherDao;
        this.mappingService = mappingService;
        this.teacherListDao = teacherListDao;
        this.courseDao = courseDao;
    }

    @Transactional
    public List<CourseListDto> getCourse(Integer idd){
        return mappingService.mapList(courseDao.getCourseByStudentIdd(idd), CourseListDto.class);
    }
}