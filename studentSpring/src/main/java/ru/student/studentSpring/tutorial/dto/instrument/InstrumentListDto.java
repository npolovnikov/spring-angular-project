package ru.student.studentSpring.tutorial.dto.instrument;

import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseListDto;

@Setter
@Getter
public class InstrumentListDto extends BaseListDto {

    private String name;
    private String number;

}
