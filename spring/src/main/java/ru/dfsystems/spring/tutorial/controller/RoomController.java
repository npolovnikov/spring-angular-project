package ru.dfsystems.spring.tutorial.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/room", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;
    private ModelMapper mapper = new ModelMapper();

    /**
     * Возвращает элементы, соответствующие параметрам
     */
    @PostMapping("/list")
    public Page<BaseListDto> getList(PageParams<RoomParams> pageParams) {
        Page<Room> page = roomService.getRoomsByParams(pageParams);
        List<BaseListDto> list = mapper(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    private List<BaseListDto> mapper(List<Room> allRooms) {
        return allRooms.stream()
                .map(r -> mapper.map(r, BaseListDto.class))
                .collect(Collectors.toList());
    }
}
