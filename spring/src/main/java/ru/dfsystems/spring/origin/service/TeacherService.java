package ru.dfsystems.spring.origin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.CourseDaoImpl;
import ru.dfsystems.spring.origin.dao.TeacherDaoImpl;
import ru.dfsystems.spring.origin.dto.Course.CourseListDto;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.teacher.TeacherDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherHistoryDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.origin.dto.teacher.TeacherParams;
import ru.dfsystems.spring.origin.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.origin.listDao.TeacherListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    private TeacherDaoImpl teacherDao;
    private MappingService mappingService;
    private TeacherListDao teacherListDao;
    private CourseDaoImpl courseDao;

    public Page<TeacherListDto> getTeachersByParams(PageParams<TeacherParams> pageParams) {
        Page <Teacher> page = teacherListDao.list(pageParams);
        List<TeacherListDto> list = mappingService.mapList(page.getList(), TeacherListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(TeacherDto teacherDto) {
        teacherDao.create(mappingService.map(teacherDto, Teacher.class));
    }

    public List<CourseListDto> getCourse(Integer idd){
        return mappingService.mapList(courseDao.getCourseByStudentIdd(idd), CourseListDto.class);
    }

    public TeacherDto get(Integer idd){
        TeacherDto dto = mappingService.map(teacherDao.getActiveByIdd(idd), TeacherDto.class);
        dto.setHistory(mappingService.mapList(teacherDao.getHistory(idd), TeacherHistoryDto.class));
        return dto;
    }

    public List<TeacherHistoryDto> getHistory(Integer idd){
        return mappingService.mapList(teacherDao.getHistory(idd), TeacherHistoryDto.class);
    }

    public void delete(Integer idd){
        Teacher teacher = teacherDao.getActiveByIdd(idd);
        teacher.setDeleteDate(LocalDateTime.now());
        teacherDao.update(teacher);
    }

    public TeacherDto update(Integer idd, TeacherDto teacherDto){
        Teacher teacher = teacherDao.getActiveByIdd(idd);
        if(teacher == null){
            throw new RuntimeException("");
        }
        teacher.setDeleteDate(LocalDateTime.now());
        teacherDao.update(teacher);
        Teacher newTeacher = mappingService.map(teacherDto, Teacher.class);
        newTeacher.setIdd(teacher.getIdd());
        teacherDao.create(newTeacher);
        return mappingService.map(teacher, TeacherDto.class);
    }

}