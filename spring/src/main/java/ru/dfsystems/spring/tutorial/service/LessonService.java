package ru.dfsystems.spring.tutorial.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonListDao;
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
    private LessonDaoImpl lessonDao;
    private LessonListDao lessonListDao;
    private MappingService mappingService;

    public Page<LessonListDto> getLessonsByParams(PageParams<LessonParams> pageParams) {
        Page<Lesson> page = lessonListDao.list(pageParams);
        List<LessonListDto> list = mappingService.mapList(page.getList(),  LessonListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
