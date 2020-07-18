package ru.dfsystems.spring.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.enums.ActionTypeEnum;
import ru.dfsystems.spring.tutorial.enums.ObjectType;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.service.QueueService;
import ru.dfsystems.spring.tutorial.service.RoomService;

@RestController
@RequestMapping(value = "/room", produces = "application/json; charset=UTF-8")
public class RoomController extends BaseController<RoomListDto, RoomDto, RoomParams, Room> {
    private RoomService roomService;
    private QueueService queueService;

    @Autowired
    public RoomController(RoomService roomService, QueueService queueService) {
        super(roomService);
        this.roomService = roomService;
        this.queueService = queueService;
    }

    @PutMapping("/{idd}/instrument")
    public void putInstrument(@PathVariable("idd") Integer idd, @RequestBody Integer instrumentIdd) {
        roomService.putInstrument(idd, instrumentIdd);
    }

    @Override
    public void create(@RequestBody RoomDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        queueService.addToQueue(ObjectType.ROOM, ActionTypeEnum.CREATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void update(@PathVariable("idd") Integer idd, @RequestBody RoomDto dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.ROOM, ActionTypeEnum.UPDATE, objectMapper.writeValueAsString(dto));
    }

    @Override
    public void delete(@PathVariable("idd") Integer idd) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RoomDto dto = new RoomDto();
        dto.setIdd(idd);
        queueService.addToQueue(ObjectType.ROOM, ActionTypeEnum.DELETE, objectMapper.writeValueAsString(dto));
    }
}
