package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.RoomDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomDaoImpl roomsDao;

    public List<Rooms> getAllRooms() {
        roomsDao.fetchOneById(12);
        roomsDao.getActiveByIdd(12);
        return roomsDao.findAll();
    }

    public Page<Rooms> getRoomsByParam(PageParams<RoomParams> pageParams) {
        return roomsDao.getRoomsByParam(pageParams);
    }
}
