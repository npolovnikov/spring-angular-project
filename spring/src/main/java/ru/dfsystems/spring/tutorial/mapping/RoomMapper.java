package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.student.StudentListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);

    RoomListDto roomToRoomListDto(Room room);

    RoomDto roomToRoomDto(Room room);

    Room roomDtoToRoom(RoomDto roomDto);

    default List<RoomListDto> roomListToRoomListDto(List<Room> rooms) {
        List<RoomListDto> roomsDto = new ArrayList<>();

        for (Room room : rooms) {
            roomsDto.add(roomToRoomListDto(room));
        }
        return roomsDto;
    }
}
