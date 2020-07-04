package ru.student.studentSpring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.student.studentSpring.tutorial.dao.LessonDaoImpl;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.dto.lesson.LessonParams;
import ru.student.studentSpring.tutorial.dto.room.RoomParams;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Lessons;
import ru.student.studentSpring.tutorial.generated.tables.pojos.Rooms;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {
    private LessonDaoImpl lessonDao;

    public List<Lessons> getAllLessons() {
        lessonDao.fetchOneById(12);
        lessonDao.getActiveByIdd(12);
        return lessonDao.findAll();
    }

    public Page<Lessons> getLessonsByParam(PageParams<LessonParams> pageParams) {
        return lessonDao.getLessonsByParam(pageParams);
    }
}
