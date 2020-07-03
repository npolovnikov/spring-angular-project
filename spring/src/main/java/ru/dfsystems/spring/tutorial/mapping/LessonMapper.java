package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Mapper
public interface LessonMapper {

    LessonMapper LESSON_MAPPER = Mappers.getMapper(LessonMapper.class);

    LessonListDto lessonToLessonListDto(Lesson lesson);

    LessonDto lessonToLessonDto(Lesson lesson);

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    default List<LessonListDto> lessonListToLessonListDto(List<Lesson> lessons) {
        List<LessonListDto> lessonsDto = new ArrayList<>();

        for (Lesson lesson: lessons) {
            lessonsDto.add(lessonToLessonListDto(lesson));
        }
        return lessonsDto;
    }

}
