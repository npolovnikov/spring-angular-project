package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.TeacherListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.TeacherDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;
import ru.dfsystems.spring.tutorial.mapping.TeacherMapper;
import ru.dfsystems.spring.tutorial.mapping.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Service
@AllArgsConstructor
public class TeacherService {

    private TeacherDaoImpl teacherDao;
    private TeacherListDao teacherListDao;

    public Page<TeacherListDto> getTeachersByParams(PageParams<TeacherParams> pageParams) {
        Page<Teacher> page = teacherListDao.list(pageParams);
        List<TeacherListDto> list = TeacherMapper.TEACHER_MAPPER.teacherListToTeacherListDto(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(TeacherDto teacherDto) {
        teacherDao.create(TeacherMapper.TEACHER_MAPPER.teacherDtoToTeacher(teacherDto));
    }

    public TeacherDto get(Integer idd) {
        return TeacherMapper.TEACHER_MAPPER.teacherToTeacherDto(teacherDao.getActiveByIdd(idd));
    }

    @Transactional
    public void delete(Integer idd) {
        Teacher teacher = teacherDao.getActiveByIdd(idd);
        teacher.setDeleteDate(LocalDateTime.now());
        teacherDao.update(teacher);
    }

    public TeacherDto update(Integer idd, TeacherDto teacherDto) {
        Teacher teacher = teacherDao.getActiveByIdd(idd);
        if (teacher == null) {
            throw new RuntimeException("ошибка");
        }
        teacher.setDeleteDate(LocalDateTime.now());
        teacherDao.update(teacher);

        Teacher newTeacher = TeacherMapper.TEACHER_MAPPER.teacherDtoToTeacher(teacherDto);
        newTeacher.setIdd(teacher.getIdd());
        teacherDao.create(newTeacher);
        return TeacherMapper.TEACHER_MAPPER.teacherToTeacherDto(newTeacher);
    }

}
