package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.list.LessonListDao;
import ru.dfsystems.spring.tutorial.dao.standard.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    private LessonListDao lessonListDao;
    private LessonDaoImpl lessonDao;
    private MappingService mappingService;

    @Transactional
    public List<Lesson> getAllLessons() {
        return lessonDao.findAll();
    }

    @Transactional
    public Page<LessonListDto> getList(PageParams<LessonParams> pageParams) {
        Page<Lesson> page = lessonListDao.getSortedList(pageParams);
        List<LessonListDto> list = mappingService.mapList(page.getList(), LessonListDto.class);

        return new Page<>(list, page.getTotalCount());
    }
}