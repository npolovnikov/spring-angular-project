package ru.student.studentSpring.tutorial.dto.room;

import lombok.Getter;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseHistoryDto;

@Getter
@Setter
public class RoomHistoryDto extends BaseHistoryDto {

    private String number;
    private String block;

}
