package ru.student.studentSpring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.service.RoomService;

import java.util.List;

@RestController
@RequestMapping(value = "/room", produces = "application/json; charset=UTF-8")
public class RoomController extends BaseController<RoomListDto, RoomDto, RoomParams, Rooms> {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        super(roomService);
        this.roomService = roomService;
    }

    @GetMapping("/{idd}/history")
    public List<RoomHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return roomService.getHistory(idd);
    }

    @PutMapping("/{idd}/instrument")
    public void putInstrument(@PathVariable(name = "idd") Integer idd, @RequestBody Integer instrumentId) {
        roomService.putInstrument(idd, instrumentId);
    }

}
