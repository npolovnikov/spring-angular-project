package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Mapper
public interface CourseMapper {

    CourseMapper COURSE_MAPPER = Mappers.getMapper(CourseMapper.class);

    CourseListDto courseToCourseListDto(Course course);

    CourseDto courseToCourseDto(Course course);

    Course courseDtoToCourse(CourseDto courseDto);

    default List<CourseListDto> courseListToCourseListDto(List<Course> courses) {
        List<CourseListDto> courseDto = new ArrayList<>();

        for (Course course: courses) {
            courseDto.add(courseToCourseListDto(course));
        }
        return courseDto;
    }

}
