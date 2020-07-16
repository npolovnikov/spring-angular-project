package ru.student.studentSpring.tutorial.dto.instrument;

import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseDto;

@Setter
@Getter
public class InstrumentDto extends BaseDto<InstrumentHistoryDto> {

    private String name;
    private String number;

}
