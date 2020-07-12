package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LessonDto {

    private Integer       id;
    private String        name;
    private String        description;

    private Integer courseIdd;
    private Integer roomIdd;

    //  private CourseListDto course;
    //  private RoomListDto room;

    private LocalDateTime lessonDateStart;
    private LocalDateTime lessonDateEnd;
    private String extraInstruments;

    private List<InstrumentListDto> instruments;
}
