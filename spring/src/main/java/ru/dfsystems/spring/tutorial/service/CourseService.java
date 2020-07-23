package ru.dfsystems.spring.tutorial.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.CourseListDao;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {

    @Autowired
    public CourseService(MappingService mappingService, CourseListDao courseListDao, CourseDaoImpl courseDao) {
        super(mappingService, courseListDao, courseDao, CourseListDto.class, CourseDto.class, Course.class);
    }

    public List<LessonListDto> getLessonsByCourseIdd(Integer idd) {
        val course = super.get(idd);
        if (course == null) throw new RuntimeException("Отсутствует курс");
        return super.get(idd).getLessons();
    }
}
