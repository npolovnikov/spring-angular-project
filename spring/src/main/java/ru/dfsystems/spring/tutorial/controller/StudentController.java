package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;
    private ModelMapper mapper = new ModelMapper();

    /**
     * Возвращает элементы, соответствующие параметрам
     */
    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<StudentParams> pageParams) {
        Page<Student> page = studentService.getStudentsByParams(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    private List<BaseListDto> mapper(List<Student> allStudents) {
        return allStudents.stream()
                .map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
