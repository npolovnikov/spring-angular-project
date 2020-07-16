package ru.student.studentSpring.tutorial.dao;

import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.generate.JooqBase;

public interface BaseListDao<Entity extends JooqBase, Params>  {
    Page<Entity> list(PageParams<Params> pageParams);
}
