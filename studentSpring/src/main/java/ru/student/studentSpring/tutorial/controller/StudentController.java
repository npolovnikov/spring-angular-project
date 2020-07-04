package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.student.StudentParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Students;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.service.StudentService;
import ru.student.studentSpring.tutorial.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;
    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<StudentParams> pageParams) {
        Page<Students> page = studentService.getStudentsByParam(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }


    private List<BaseListDto> mapper(List<Students> allStudents) {
        return allStudents
                .stream().map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
