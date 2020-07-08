package ru.dfsystems.spring.origin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.CourseDaoImpl;
import ru.dfsystems.spring.origin.dto.Course.CourseDto;
import ru.dfsystems.spring.origin.dto.Course.CourseListDto;
import ru.dfsystems.spring.origin.dto.Course.CourseParams;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.generated.tables.pojos.Course;
import ru.dfsystems.spring.origin.listDao.CourseListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.time.LocalDateTime;
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

    public CourseDto get(Integer idd) {
        CourseDto dto = mappingService.map(courseDao.getActiveByIdd(idd), CourseDto.class);
        return dto;
    }

    public void create(CourseDto courseDto) {
        courseDao.create(mappingService.map(courseDto, Course.class));
    }

    public void delete(Integer idd){
        Course course = courseDao.getActiveByIdd(idd);
        course.setDeleteDate(LocalDateTime.now());
        courseDao.update(course);
    }

    public CourseDto update(Integer idd, CourseDto courseDto) {
        Course course = courseDao.getActiveByIdd(idd);
        if(course == null){
            throw new RuntimeException("");
        }
        course.setDeleteDate(LocalDateTime.now());
        courseDao.update(course);
        Course newCourse = mappingService.map(courseDto, Course.class);
        newCourse.setIdd(course.getIdd());
        courseDao.create(newCourse);
        return mappingService.map(course, CourseDto.class);
    }
}
