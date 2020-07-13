package ru.dfsystems.spring.tutorial.dao.lesson;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.generated.tables.daos.LessonDao;

@Repository
@AllArgsConstructor
public class LessonDaoImpl extends LessonDao {
    private final DSLContext jooq;
}
