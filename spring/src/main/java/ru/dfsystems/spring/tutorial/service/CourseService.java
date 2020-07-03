package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.CourseListDao;
import ru.dfsystems.spring.tutorial.dao.standard.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseListDao courseListDao;
    private CourseDaoImpl courseDao;
    private MappingService mappingService;

    @Transactional
    public List<Course> getAllCourses() {
        courseDao.fetchOneById(152);
        courseDao.getActiveByIdd(152);
        return courseDao.findAll();
    }

    @Transactional
    public Page<CourseListDto> getList(PageParams<CourseParams> pageParams) {
        Page<Course> page = courseListDao.getSortedList(pageParams);
        List<CourseListDto> list = mappingService.mapList(page.getList(), CourseListDto.class);

        return new Page<>(list, page.getTotalCount());
    }
}