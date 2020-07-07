package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Mapper
public interface CourseMapper extends BaseMapper<Course, CourseDto, CourseListDto> {

    CourseListDto courseToCourseListDto(Course course);

    CourseDto courseToCourseDto(Course course);

    Course courseDtoToCourse(CourseDto courseDto);

    default List<CourseListDto> courseListToCourseListDto(List<Course> courses) {
        List<CourseListDto> courseDto = new ArrayList<>();
        for (Course course : courses) {
            courseDto.add(courseToCourseListDto(course));
        }
        return courseDto;
    }

}
