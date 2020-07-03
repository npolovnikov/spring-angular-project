package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.TeacherListDao;
import ru.dfsystems.spring.tutorial.dao.standard.TeacherDaoImpl;
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

    private TeacherListDao teacherListDao;
    private TeacherDaoImpl teacherDao;
    private MappingService mappingService;

    @Transactional
    public List<Teacher> getAll() {
        return teacherDao.findAll();
    }

    @Transactional
    public Page<TeacherListDto> getList(PageParams<TeacherParams> pageParams) {
        Page<Teacher> page = teacherListDao.getSortedList(pageParams);
        List<TeacherListDto> list = mappingService.mapList(page.getList(), TeacherListDto.class);

        return new Page <>(list, page.getTotalCount());
    }
}