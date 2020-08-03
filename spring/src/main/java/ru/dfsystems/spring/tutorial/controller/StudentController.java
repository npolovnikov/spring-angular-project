package ru.dfsystems.spring.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentParams;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;
import ru.dfsystems.spring.tutorial.service.QueueService;
import ru.dfsystems.spring.tutorial.service.StudentService;

@RestController
@RequestMapping(value = "/student", produces = "application/json; charset=UTF-8")
public class StudentController extends BaseController<StudentListDto, StudentDto, StudentParams, Student> {
    StudentService studentService;
    private QueueService queueService;

    @Autowired
    public StudentController(StudentService studentService, QueueService queueService) {
        super(studentService);
        this.studentService = studentService;
        this.queueService = queueService;
    }

    /**
     * Возвращает курсы, на которые записан студент
     */
    @PostMapping("/{idd}/course/list")
    public Page<CourseListDto> getCoursesByStudentIdd(@PathVariable("idd") Integer idd) {
        return studentService.getCoursesByStudentIdd(idd);
    }

    /**
     * Запись сутдента на новый курс
     */
    @PutMapping("/{idd}/course")
    public void putCourse(@PathVariable("idd") Integer idd, @RequestBody Integer courseIdd) {
        studentService.putCourse(idd, courseIdd);
    }

    @Override
    public void create(@RequestBody StudentDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        /* мапим дто в строку */
        queueService.addToQueue(ObjectType.STUDENT, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(@PathVariable("idd") Integer idd, @RequestBody StudentDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.STUDENT, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void delete(@PathVariable("idd") Integer idd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        StudentDto dto = new StudentDto();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.STUDENT, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}
