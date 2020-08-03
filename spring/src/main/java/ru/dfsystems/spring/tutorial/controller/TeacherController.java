package ru.dfsystems.spring.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.service.QueueService;
import ru.dfsystems.spring.tutorial.service.TeacherService;

@RestController
@RequestMapping(value = "/teacher", produces = "application/json; charset=UTF-8")
public class TeacherController extends BaseController<TeacherListDto, TeacherDto, TeacherParams, Teacher> {
    TeacherService teacherService;
    private QueueService queueService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        super(teacherService);
        this.teacherService = teacherService;
    }

    /**
     * Возвращает список курсов учителя
     */
    @PostMapping("/{idd}/course/list")
    public Page<CourseListDto> getCoursesByTeacherIdd(@PathVariable("idd") Integer idd) {
        return teacherService.getCoursesByTeacherIdd(idd);
    }

    @Override
    public void create(@RequestBody TeacherDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        /* мапим дто в строку */
        queueService.addToQueue(ObjectType.TEACHER, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(@PathVariable("idd") Integer idd, @RequestBody TeacherDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.TEACHER, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void delete(@PathVariable("idd") Integer idd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeacherDto dto = new TeacherDto();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.TEACHER, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}
