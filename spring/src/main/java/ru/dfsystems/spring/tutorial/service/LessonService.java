package ru.dfsystems.spring.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonDaoImpl;
import ru.dfsystems.spring.tutorial.dao.lesson.LessonListDao;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonListDto;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;
import ru.dfsystems.spring.tutorial.mapping.MappingService;

import java.util.List;

@Service
public class LessonService extends BaseService<LessonListDto, LessonDto, LessonParams, Lesson> {
    private LessonListDao lessonListDao;
    private MappingService mappingService;

    @Autowired
    public LessonService(LessonListDao lessonListDao,
                         LessonDaoImpl lessonDao,
                         MappingService mappingService) {
        super(mappingService, lessonListDao, lessonDao, LessonListDto.class, LessonDto.class, Lesson.class);
        this.mappingService = mappingService;
        this.lessonListDao = lessonListDao;
    }

    @Transactional
    public Page<LessonListDto> getLessonsByParams(PageParams<LessonParams> pageParams) {
        Page<Lesson> page = lessonListDao.list(pageParams);
        List<LessonListDto> list = mappingService.mapList(page.getList(), LessonListDto.class);
        return new Page<>(list, page.getTotalCount());
    }
}
