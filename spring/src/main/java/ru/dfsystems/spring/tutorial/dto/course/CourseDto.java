package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentListDto;

import java.util.List;

@Getter
@Setter
public class CourseDto extends BaseDto<CourseHistoryDto> {
    private String name;
    private String description;
    private String teacherId;
    private String maxCountStudent;
    private String startDate;
    private String endDate;

    private List<InstrumentListDto> instruments;
}
