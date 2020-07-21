package ru.dfsystems.spring.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.QueueService;
import ru.dfsystems.spring.tutorial.service.StudentService;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
@Api(tags =  {"Студенты"}, description = "API для студентов")
public class StudentController  extends BaseController<StudentDto, StudentDto, StudentParams, Student>{
    private StudentService studentService;
    private QueueService queueService;
    private ObjectType objType = ObjectType.STUDENT;

    @Autowired
    public StudentController(StudentService studentService, QueueService queueService) {
        super(studentService);
        this.studentService = studentService;
        this.queueService = queueService;
    }

    @Override
    public void create(@RequestBody StudentDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        queueService.addToQueue(objType, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(@PathVariable("idd") Integer idd, @RequestBody StudentDto dto) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        dto.setIdd(idd);
//        queueService.addToQueue(objType, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
        studentService.update(idd, dto);
    }

    @Override
    public void delete(@PathVariable("idd") Integer idd) throws Exception {
        studentService.delete(idd, null);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        StudentDto dto = new StudentDto();
//        dto.setIdd(idd);
//        queueService.addToQueue(objType, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}
