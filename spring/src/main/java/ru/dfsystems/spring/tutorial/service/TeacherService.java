package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    private TeacherDaoImpl teacherDao;

    public List<Teacher> getAllTeachers() {
        teacherDao.fetchOneById(152);
        teacherDao.getActiveByIdd(152);
        return teacherDao.findAll();
    }

    public Page<Teacher> getTeachersByParams(PageParams<TeacherParams> pageParams) {
        return teacherDao.getTeachersByParams(pageParams);
    }

}
