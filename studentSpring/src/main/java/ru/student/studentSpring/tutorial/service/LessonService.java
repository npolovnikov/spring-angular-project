package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.student.studentSpring.tutorial.dao.CourseDaoImpl;
import ru.student.studentSpring.tutorial.dao.LessonDaoImpl;
import ru.student.studentSpring.tutorial.dao.LessonListDao;
import ru.student.studentSpring.tutorial.dao.RoomDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.course.CourseListDto;
import ru.student.studentSpring.tutorial.dto.lesson.LessonDto;
import ru.student.studentSpring.tutorial.dto.lesson.LessonListDto;
import ru.student.studentSpring.tutorial.dto.lesson.LessonParams;
import ru.student.studentSpring.tutorial.dto.room.RoomListDto;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Lessons;
import ru.student.studentSpring.tutorial.mapping.MappingService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    private final LessonListDao lessonListDao;
    private final LessonDaoImpl lessonDao;
    private final MappingService mappingService;
    private final CourseDaoImpl courseDao;
    private final RoomDaoImpl roomDao;

    public Page<LessonListDto> getLessonsByParam(PageParams<LessonParams> pageParams) {
        Page<Lessons> page = lessonListDao.list(pageParams);
        List<LessonListDto> list = mappingService.mapList(page.getList(), LessonListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(LessonDto lessonDto) {
        lessonDao.create(mappingService.map(lessonDto, Lessons.class));
    }

    public LessonDto get(Integer idd) {
        LessonDto dto = mappingService.map(lessonDao.getActiveByIdd(idd), LessonDto.class);
        dto.setCourse(mappingService.mapList(courseDao.getCourses(idd), CourseListDto.class));
        dto.setRoom(mappingService.mapList(roomDao.getRooms(idd), RoomListDto.class));

        return dto;
    }


    @Transactional
    public void delete(Integer idd) {
        Lessons lesson = lessonDao.getActiveByIdd(idd);
        lesson.setLessonDateEnd(LocalDateTime.now());
        lessonDao.update(lesson);
    }


    @Transactional
    public LessonDto update(Integer idd, LessonDto lessonDto) {
        Lessons lesson = lessonDao.getActiveByIdd(idd);
        if (lesson == null) {
            throw new RuntimeException("");
        }
        lesson.setLessonDateEnd(LocalDateTime.now());
        lessonDao.update(lesson);
        Lessons newLesson = mappingService.map(lessonDto, Lessons.class);
        lessonDao.create(newLesson);
        return mappingService.map(lesson, LessonDto.class);
    }

}
