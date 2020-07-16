package ru.dfsystems.spring.tutorial.service;

import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.BaseMapping;

@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> {


}
