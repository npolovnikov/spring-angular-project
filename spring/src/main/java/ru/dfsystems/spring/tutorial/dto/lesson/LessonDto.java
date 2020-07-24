package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.generated.tables.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.Room;

import java.util.List;

@Getter
@Setter
public class LessonDto  extends BaseDto<LessonHistoryDto> {
    private String name;
    private String description;
    private CourseListDto course;
    private RoomListDto room;

    private List<InstrumentListDto> instruments;
}
