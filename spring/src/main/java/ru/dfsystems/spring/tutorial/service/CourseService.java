package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.course.CourseListDao;
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
    private CourseDaoImpl courseDao;
    private CourseListDao courseListDao;
    private MappingService mappingService;

    public Page<CourseListDto> getCoursesByParams(PageParams<CourseParams> pageParams) {
        Page<Course> page = courseListDao.list(pageParams);
        List<CourseListDto> list = mappingService.mapList(page.getList(), CourseListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
