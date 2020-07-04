package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.TeacherDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {

    private TeacherDaoImpl teacherDao;

    public List<Teachers> getAllTeachers() {
        teacherDao.fetchOneById(12);
        teacherDao.getActiveByIdd(12);
        return teacherDao.findAll();
    }

    public Page<Teachers> getTeachersByParam(PageParams<TeacherParams> pageParams) {
        return teacherDao.getTeachersByParam(pageParams);
    }
}
