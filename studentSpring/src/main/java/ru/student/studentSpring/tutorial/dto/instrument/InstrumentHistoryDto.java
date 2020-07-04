package ru.student.studentSpring.tutorial.dto.instrument;


import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseHistoryDto;

@Setter
@Getter
public class InstrumentHistoryDto extends BaseHistoryDto {

    private String name;
    private String number;
}
