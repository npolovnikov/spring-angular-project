package ru.dfsystems.spring.tutorial.dao;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.dto.course.CourseParams;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.Course;

@Repository
@AllArgsConstructor
public class CourseListDao implements BaseListDao<Course, CourseParams> {

    private final DSLContext jooq;

    @Override
    public Page<Course> list(PageParams<CourseParams> pageParams) {
        return null;
    }
}
