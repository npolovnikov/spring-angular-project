package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dao.list.CourseListDao;
import ru.dfsystems.spring.tutorial.dao.list.RoomListDao;
import ru.dfsystems.spring.tutorial.dao.standard.CourseDaoImpl;
import ru.dfsystems.spring.tutorial.dao.standard.RoomDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseListDto;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.dto.room.RoomDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomListDto;
import ru.dfsystems.spring.tutorial.dto.room.RoomParams;
import ru.dfsystems.spring.tutorial.generated.tables.daos.InstrumentToRoomDao;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Room;
import ru.dfsystems.spring.tutorial.mapping.BaseMapping;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class CourseService extends BaseService<CourseListDto, CourseDto, CourseParams, Course> {

    public CourseService(CourseListDao courseListDao, CourseDaoImpl courseDao, MappingService mappingService) {
        super(mappingService, courseListDao, courseDao, CourseListDto.class, CourseDto.class, Course.class);
    }
}