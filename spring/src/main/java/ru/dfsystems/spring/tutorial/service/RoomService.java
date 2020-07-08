package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.generated.tables.daos.RoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomDao roomDao;

    public List<Room> getAllRooms(){
        return roomDao.findAll();
    }
}
