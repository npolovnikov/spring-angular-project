package ru.dfsystems.spring.tutorial.dto.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class RoomListDto extends BaseListDto {

    private String number;
    private String block;

}
