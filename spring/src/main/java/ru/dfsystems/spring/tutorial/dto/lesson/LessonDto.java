package ru.dfsystems.spring.tutorial.dto.lesson;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseHistoryDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Getter
@Setter
public class LessonDto extends BaseDto<BaseHistoryDto> {

    private String name;
    private String description;
    private Integer course_idd;
    private Integer room_idd;
    private LocalDateTime lesson_date_start;
    private LocalDateTime lesson_date_end;

    private List<InstrumentListDto> extraInstruments;


}
