package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;

import java.time.LocalDateTime;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */

@Getter
@Setter
public class LessonHistoryDto extends BaseHistoryDto {

    private String name;
    private String description;
    private Integer course_idd;
    private Integer room_idd;
    private LocalDateTime lesson_date_start;
    private LocalDateTime lesson_date_end;

}
