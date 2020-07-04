package ru.student.studentSpring.tutorial.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String extra_instruments;
    private CourseListDto course;
    private RoomListDto room;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lesson_date_start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lesson_date_end;
}
