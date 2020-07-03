package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.generated.tables.daos.StudentDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentMapper STUDENT_MAPPER = Mappers.getMapper(StudentMapper.class);

    StudentListDto studentToStudentListDto(Student student);

    StudentDto studentToStudentDto(Student student);

    Student studentDtoToStudent(StudentDto studentDto);

    default List<StudentListDto> studentListToStudentListDto(List<Student> students) {
        List<StudentListDto> studentsDto = new ArrayList<>();

        for (Student student : students) {
            studentsDto.add(studentToStudentListDto(student));
        }
        return studentsDto;
    }
}
