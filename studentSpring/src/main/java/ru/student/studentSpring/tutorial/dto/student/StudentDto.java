package ru.student.studentSpring.tutorial.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseDto;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.instrument.InstrumentListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherHistoryDto;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class StudentDto extends BaseDto<StudentHistoryDto> {
    private String firstName;
    private String middleName;
    private String lastName;
    private String passport;
    private String status;

    private List<StudentHistoryDto> history;
    private List<CourseListDto> courses;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime birthDate;
}
