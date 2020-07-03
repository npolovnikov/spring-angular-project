package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.StudentListDao;
import ru.dfsystems.spring.tutorial.dao.standard.StudentDaoImpl;
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

    private StudentListDao studentListDao;
    private StudentDaoImpl studentDao;
    private MappingService mappingService;

    @Transactional
    public List<Student> getAllStudents() {
//        studentDao.fetchOneById(152);
//        studentDao.getActiveByIdd(152);
        return studentDao.findAll();
    }

    @Transactional
    public Page<StudentListDto> getList(PageParams<StudentParams> pageParams) {
        Page<Student> page = studentListDao.getSortedList(pageParams);
        List<StudentListDto> list = mappingService.mapList(page.getList(), StudentListDto.class);

        return new Page<>(list, page.getTotalCount());
    }
}
