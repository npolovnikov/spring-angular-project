package ru.student.studentSpring.tutorial.dto.room;

import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseHistoryDto;

import java.util.List;

@Getter
@Setter
public class RoomHistoryDto extends BaseHistoryDto {

    private String number;
    private String block;
    private List<RoomHistoryDto> history;
}
