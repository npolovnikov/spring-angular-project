package ru.dfsystems.spring.origin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dfsystems.spring.origin.dao.LessonDaoImpl;
import ru.dfsystems.spring.origin.dto.Page;
import ru.dfsystems.spring.origin.dto.PageParams;
import ru.dfsystems.spring.origin.dto.lesson.LessonDto;
import ru.dfsystems.spring.origin.dto.lesson.LessonListDto;
import ru.dfsystems.spring.origin.dto.lesson.LessonParams;
import ru.dfsystems.spring.origin.generated.tables.Lesson;
import ru.dfsystems.spring.origin.listDao.LessonListDao;
import ru.dfsystems.spring.origin.mapping.MappingService;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {
    private LessonDaoImpl lessonDao;
    private LessonListDao lessonListDao;
    private MappingService mappingService;

    public Page<LessonListDto> getLessonsByParams(PageParams<LessonParams> pageParams) {
        Page<Lesson> page = lessonListDao.list(pageParams);
        List<LessonListDto> list = mappingService.mapList(page.getList(), LessonListDto.class);
        return new Page<>(list, page.getTotalCount());
    }

    public LessonDto get(Integer id){
        LessonDto dto = mappingService.map(lessonDao.getActiveById(id), LessonDto.class);
        return dto;
    }

}