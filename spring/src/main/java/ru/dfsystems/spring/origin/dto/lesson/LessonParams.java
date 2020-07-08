package ru.dfsystems.spring.origin.dto.lesson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.dfsystems.spring.origin.dto.instrument.InstrumentListDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonParams {
    private Integer idd;
    private String name;
    private String description;
    private String orderBy;
    private String orderDir;
    private Integer courseId;
    private Integer roomId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lessonDateStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lessonDateEnd;
    private String extraInstruments;
    private List<InstrumentListDto> instruments;
}
