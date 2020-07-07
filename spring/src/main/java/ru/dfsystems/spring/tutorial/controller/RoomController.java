package ru.dfsystems.spring.tutorial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.service.BaseService;
import ru.dfsystems.spring.tutorial.service.RoomService;

@RestController
@Tag(name = "Аудиатория", description = "Api Аудиатория")
@RequestMapping(value = "/room", produces = "application/json; charset=UTF-8")
public class RoomController extends BaseController<RoomListDto, RoomDto, RoomParams, Room> {

    private RoomService roomService;

    @Autowired
    public RoomController(BaseService<RoomListDto, RoomDto, RoomParams, Room> service, RoomService roomService) {
        super(service);
        this.roomService = roomService;
    }

    @PostMapping("/list")
    @Operation(summary = "Список аудиаторий", description = "", tags = {"room"})
    public Page<RoomListDto> getList(@RequestBody PageParams<RoomParams> pageParams) {
        return roomService.list(pageParams);
    }

    @PostMapping
    @Operation(summary = "Добавить аудиаторию", description = "", tags = {"room"})
    public void create(@RequestBody RoomDto roomDto) {
        roomService.create(roomDto);
    }

    @GetMapping("/{idd}")
    @Operation(summary = "Информация об аудиатории", description = "", tags = {"room"})
    public RoomDto get(@PathVariable("idd") Integer idd) {
        return roomService.get(idd);
    }

    @PatchMapping("/{idd}")
    @Operation(summary = "Обновить информацию об аудиатории", description = "", tags = {"room"})
    public RoomDto update(@PathVariable("idd") Integer idd, @RequestBody RoomDto roomDto) {
        return roomService.update(idd, roomDto);
    }

    @DeleteMapping("/{idd}")
    @Operation(summary = "Удалить аудиаторию", description = "", tags = {"room"})
    public void delete(@PathVariable("idd") Integer idd) {
        roomService.delete(idd);
    }

    @PutMapping("/{idd}/instrument")
    @Operation(summary = "Добавить инструмент для аудиатории", description = "", tags = {"room"})
    public void putInstrument(@PathVariable("idd") Integer idd, @RequestBody Integer instrumentIdd) {
        roomService.putInstrument(idd, instrumentIdd);
    }

}
