package ru.dfsystems.spring.tutorial.dto.instrument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentListDto extends BaseListDto {
    private String name;
    private String number;

}
