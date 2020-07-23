package ru.dfsystems.spring.tutorial.controller;

import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.RoomService;

@RestController
@RequestMapping(value = "/room", produces = "application/json; charset=UTF-8")
public class RoomController extends BaseController<RoomListDto, RoomDto, RoomParams, Room> {
    private RoomService roomService;


    public RoomController(BaseService<RoomListDto, RoomDto, RoomParams, Room> service, RoomService roomService) {
        super(service);
        this.roomService = roomService;
    }

    @PutMapping("/{idd}/instrument")
    public void putInstrument(@PathVariable("idd") Integer idd, @RequestBody Integer instrumentIdd) {
        roomService.putInstrument(idd, instrumentIdd);
    }
}
