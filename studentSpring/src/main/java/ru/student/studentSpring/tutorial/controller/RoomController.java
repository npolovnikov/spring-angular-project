package ru.student.studentSpring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.room.RoomDto;
import ru.student.studentSpring.tutorial.dto.room.RoomHistoryDto;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;
import ru.student.studentSpring.tutorial.service.RoomService;

import java.util.List;

@RestController
@RequestMapping(value = "/room", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/list")
    public Page<RoomListDto> getList(@RequestBody PageParams<RoomParams> pageParams) {

        return roomService.getRoomsByParam(pageParams);
    }

    @PostMapping
    public void create(@RequestBody RoomDto roomDto) {
        roomService.create(roomDto);
    }

    @GetMapping("/{idd}")
    public RoomDto get(@PathVariable(name = "idd") Integer idd) {

        return roomService.get(idd);
    }

    @PatchMapping("/{idd}")
    public RoomDto update(@PathVariable(name = "idd") Integer idd, @RequestBody RoomDto roomDto) {

        return roomService.update(idd, roomDto);
    }

    @GetMapping("/{idd}/history")
    public List<RoomHistoryDto> getHistory(@PathVariable(name = "idd") Integer idd) {
        return roomService.getHistory(idd);
    }
    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        roomService.delete(idd);
    }

    @PutMapping("/{idd}/instrument")
    public void putInstrument(@PathVariable(name = "idd") Integer idd, @RequestBody Integer instrumentId) {
        roomService.putInstrument(idd, instrumentId);
    }

}
