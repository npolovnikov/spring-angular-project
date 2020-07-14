package ru.student.studentSpring.tutorial.dto.instrument;

import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;

import java.util.List;

@Setter
@Getter
public class InstrumentDto extends BaseDto<InstrumentHistoryDto> {

    private String name;
    private String number;

}
