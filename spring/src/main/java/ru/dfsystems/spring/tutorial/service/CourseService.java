package ru.dfsystems.spring.tutorial.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.course.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.course.CourseListDao;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {
    MappingService mappingService;
    LessonDaoImpl lessonDao;

    @Autowired
    public CourseService(CourseListDao courseListDao,
                         CourseDaoImpl courseDao,
                         LessonDaoImpl lessonDao,
                         MappingService mappingService) {
        super(mappingService, courseListDao, courseDao, CourseListDto.class, CourseDto.class, Course.class);
        this.lessonDao = lessonDao;
        this.mappingService = mappingService;
    }

    public Page<LessonListDto> getLessonsByCourseIdd(Integer idd) {
        List<LessonListDto> list = mappingService.mapList(lessonDao.getLessonsByCourseIdd(idd), LessonListDto.class);
        return new Page<>(list, (long) list.size()); //?
    }

    @Override
    protected void doCreate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        CourseDto dto = om.readValue(objectData, CourseDto.class);
        create(dto, userId);
    }

    @Override
    protected void doUpdate(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        CourseDto dto = om.readValue(objectData, CourseDto.class);
        update(dto.getIdd(), dto, userId);
    }

    @Override
    protected void doDelete(String objectData, Integer userId) throws Exception {
        ObjectMapper om = new ObjectMapper();
        CourseDto dto = om.readValue(objectData, CourseDto.class);
        delete(dto.getIdd(), userId);
    }
}
