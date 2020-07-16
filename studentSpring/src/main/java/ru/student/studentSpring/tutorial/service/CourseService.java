package ru.student.studentSpring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.BaseListDao;
import ru.student.studentSpring.tutorial.dao.CourseDaoImpl;
import ru.student.studentSpring.tutorial.dao.CourseListDao;
import ru.student.studentSpring.tutorial.dao.TeacherDaoImpl;
import ru.student.studentSpring.tutorial.dto.course.CourseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseHistoryDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.course.CoursesParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Courses;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CoursesParams, Courses> {

    private CourseDaoImpl courseDao;
    private MappingService mappingService;


    @Autowired
    public CourseService(CourseListDao courseListDao, CourseDaoImpl courseDao,
                         MappingService mappingService) {
        super(mappingService, courseListDao, CourseListDto.class, courseDao, CourseDto.class, Courses.class);
    }

    public List<CourseHistoryDto> getHistory(Integer idd) {

        return mappingService.mapList(courseDao.getHistory(idd), CourseHistoryDto.class);
    }


}
