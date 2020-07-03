package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Mapper
public interface TeacherMapper {

    TeacherMapper TEACHER_MAPPER = Mappers.getMapper(TeacherMapper.class);

    TeacherListDto teacherToTeacherListDto(Teacher teacher);

    TeacherDto teacherToTeacherDto(Teacher teacher);

    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

    default List<TeacherListDto> teacherListToTeacherListDto(List<Teacher> teachers) {
        List<TeacherListDto> teachersDto = new ArrayList<>();

        for (Teacher teacher : teachers) {
            teachersDto.add(teacherToTeacherListDto(teacher));
        }
        return teachersDto;
    }
}
