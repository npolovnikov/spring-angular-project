package ru.student.studentSpring.tutorial.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.teacher.TeacherListDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String extraInstruments;

    private List<RoomListDto> room;
    private List<CourseListDto> course;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime lessonDateStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime lessonDateEnd;
}
