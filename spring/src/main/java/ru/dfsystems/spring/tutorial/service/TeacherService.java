package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherListDao;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> {

    private TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherListDao teacherListDao, TeacherDaoImpl teacherDaoImpl,
                          TeacherDao teacherDao, MappingService mappingService) {
        super(mappingService, teacherListDao, teacherDaoImpl, TeacherListDto.class,
                TeacherDto.class, Teacher.class);
        this.teacherDao = teacherDao;
    }

}
