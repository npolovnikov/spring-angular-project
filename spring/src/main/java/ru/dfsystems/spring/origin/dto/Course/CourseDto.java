package ru.dfsystems.spring.origin.dto.Course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.origin.dto.BaseDto;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class CourseDto extends BaseDto<CourseHistoryDto> {
    private String name;
    private String description;
    private String status;
    private Integer teacherId;
    private Integer maxCountStudent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateEnd;
}
