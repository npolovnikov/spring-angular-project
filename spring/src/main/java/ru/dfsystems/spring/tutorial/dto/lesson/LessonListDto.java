package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessonListDto {

    private Integer       id;
    private String        name;
    private String        description;

    private Integer       courseIdd;
    private Integer       roomIdd;

    private LocalDateTime lessonDateStart;
    private LocalDateTime lessonDateEnd;
    private String  extraInstruments;

}
