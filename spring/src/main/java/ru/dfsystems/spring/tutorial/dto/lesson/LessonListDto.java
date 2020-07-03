package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Getter
@Setter
public class LessonListDto extends BaseListDto {

    private String name;
    private String description;
    private Integer courseIdd;
    private Integer roomIdd;
    private LocalDateTime lessonDateStart;
    private LocalDateTime lessonDateEnd;

}
