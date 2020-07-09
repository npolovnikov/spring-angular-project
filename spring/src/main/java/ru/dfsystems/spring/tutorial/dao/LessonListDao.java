package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.instrument.InstrumentParams;
import ru.dfsystems.spring.tutorial.dto.lesson.LessonParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Instrument;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Lesson;

@Repository
@AllArgsConstructor
public class LessonListDao implements BaseListDao<Lesson, LessonParams> {
    private final DSLContext jooq;

    @Override
    public Page<Lesson> list(PageParams<LessonParams> pageParams) {
        return null;
    }
}
