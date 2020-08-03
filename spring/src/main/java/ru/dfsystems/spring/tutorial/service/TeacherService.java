package ru.dfsystems.spring.tutorial.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> {
    CourseDaoImpl courseDao;
    MappingService mappingService;

    @Autowired
    public TeacherService(TeacherListDao teacherListDao,
                          TeacherDaoImpl teacherDao,
                          CourseDaoImpl courseDao,
                          MappingService mappingService) {
        super(mappingService, teacherListDao, teacherDao, TeacherListDto.class, TeacherDto.class, Teacher.class);
        this.mappingService = mappingService;
        this.courseDao = courseDao;
    }

    @Transactional
    public Page<CourseListDto> getCoursesByTeacherIdd(Integer idd) {
        List<CourseListDto> list = mappingService.mapList(courseDao.getCoursesByTeacherIdd(idd), CourseListDto.class);
        return new Page<>(list, (long) list.size()); //?
    }

    @Override
    protected void doCreate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        TeacherDto dto = om.readValue(objectData, TeacherDto.class);
        create(dto, userId);
    }

    @Override
    protected void doUpdate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        TeacherDto dto = om.readValue(objectData, TeacherDto.class);
        update(dto.getIdd(), dto, userId);
    }

    @Override
    protected void doDelete(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        TeacherDto dto = om.readValue(objectData, TeacherDto.class);
        delete(dto.getIdd(), userId);
    }
}
