package ru.student.studentSpring.tutorial.service;

import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.TeacherDaoImpl;
import ru.student.studentSpring.tutorial.dao.TeacherListDao;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teachers> {

    private TeacherDaoImpl teacherDao;
    private MappingService mappingService;

    public TeacherService(TeacherListDao teacherListDao, TeacherDaoImpl teacherDao,
                          MappingService mappingService) {
        super(mappingService, teacherListDao, TeacherListDto.class,
                teacherDao, TeacherDto.class, Teachers.class);
    }

    public List<TeacherHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(teacherDao.getHistory(idd), TeacherHistoryDto.class);
    }

}
