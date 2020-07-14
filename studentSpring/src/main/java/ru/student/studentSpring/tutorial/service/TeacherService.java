package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.TeacherDaoImpl;
import ru.student.studentSpring.tutorial.dao.TeacherListDao;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherDaoImpl teacherDao;
    private final MappingService mappingService;
    private final TeacherListDao teacherListDao;

    public Page<TeacherListDto> getTeachersByParam(PageParams<TeacherParams> pageParams) {
        Page<Teachers> page = teacherListDao.list(pageParams);
        List<TeacherListDto> list = mappingService.mapList(page.getList(), TeacherListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
    @Transactional
    public void create(TeacherDto teacherDto) {

        teacherDao.create(mappingService.map(teacherDto, Teachers.class));
    }

    public TeacherDto get(Integer idd) {
        TeacherDto dto = mappingService.map(teacherDao.getActiveByIdd(idd), TeacherDto.class);
        dto.setHistory(getHistory(idd));
        return dto;
    }

    public List<TeacherHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(teacherDao.getHistory(idd), TeacherHistoryDto.class);
    }

    @Transactional
    public void delete(Integer idd) {
        Teachers teacher = teacherDao.getActiveByIdd(idd);
        teacher.setDeleteDate(LocalDateTime.now());
        teacherDao.update(teacher);
    }


    @Transactional
    public TeacherDto update(Integer idd, TeacherDto teacherDto) {
        Teachers teacher = teacherDao.getActiveByIdd(idd);
        if (teacher == null) {
            throw new RuntimeException("");
        }
        teacher.setDeleteDate(LocalDateTime.now());
        teacherDao.update(teacher);
        Teachers newTeacher = mappingService.map(teacherDto, Teachers.class);
        newTeacher.setIdd(teacher.getIdd());
        teacherDao.create(newTeacher);
        return mappingService.map(teacher, TeacherDto.class);
    }
}
