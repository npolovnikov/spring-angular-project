package ru.student.studentSpring.tutorial.dto.instrument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseListDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentListDto extends BaseListDto {

    private String name;
    private String number;

}
