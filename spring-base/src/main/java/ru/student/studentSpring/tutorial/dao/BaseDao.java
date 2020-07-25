package ru.student.studentSpring.tutorial.dao;

import ru.student.studentSpring.tutorial.generate.JooqBase;

public interface BaseDao<Entity extends JooqBase> {
    Entity create(Entity entity);

    Entity getActiveByIdd(Integer idd);

    void update(Entity room);
}
