package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Teachers;
import ru.student.studentSpring.tutorial.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class TeacherController {
    private TeacherService teacherService;
    private ModelMapper mapper = new ModelMapper();

    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<TeacherParams> pageParams) {
        Page<Teachers> page = teacherService.getTeachersByParam(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }


    private List<BaseListDto> mapper(List<Teachers> allRooms) {
        return allRooms
                .stream().map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
