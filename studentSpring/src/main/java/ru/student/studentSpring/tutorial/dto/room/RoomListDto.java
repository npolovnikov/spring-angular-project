package ru.student.studentSpring.tutorial.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.student.studentSpring.tutorial.dto.BaseListDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomListDto extends BaseListDto {
    private String number;
    private String block;

}
