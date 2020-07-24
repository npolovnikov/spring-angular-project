package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;

import java.util.List;

@Getter
@Setter
public class LessonHistoryDto extends BaseHistoryDto {
    private String name;
    private String description;
    private CourseListDto course;
    private RoomListDto room;
}
