package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dao.teacher.TeacherListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    private TeacherDaoImpl teacherDao;
    private TeacherListDao teacherListDao;
    private MappingService mappingService;

    public Page<TeacherListDto> getTeachersByParams(PageParams<TeacherParams> pageParams) {
        Page<Teacher> page = teacherListDao.list(pageParams);
        List<TeacherListDto> list = mappingService.mapList(page.getList(),  TeacherListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
