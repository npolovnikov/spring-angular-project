package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.student.StudentDaoImpl;
import ru.dfsystems.spring.tutorial.dao.student.StudentListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private StudentDaoImpl studentDao;
    private StudentListDao studentListDao;
    private MappingService mappingService;

    public Page<StudentListDto> getStudentsByParams(PageParams<StudentParams> pageParams) {
        Page<Student> page = studentListDao.list(pageParams);
        List<StudentListDto> list = mappingService.mapList(page.getList(),  StudentListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
