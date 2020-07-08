package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dao.list.RoomListDao;
import ru.dfsystems.spring.tutorial.dao.list.TeacherListDao;
import ru.dfsystems.spring.tutorial.dao.standard.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dao.standard.TeacherDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherListDto;
import ru.dfsystems.spring.tutorial.dto.teacher.TeacherParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Teacher;
import ru.dfsystems.spring.tutorial.mapping.BaseMapping;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class TeacherService extends BaseService<TeacherListDto, TeacherDto, TeacherParams, Teacher> {

    public TeacherService(TeacherListDao teacherListDao, TeacherDaoImpl teacherDao, MappingService mappingService) {
        super(mappingService, teacherListDao, teacherDao, TeacherListDto.class, TeacherDto.class, Teacher.class);
    }
}