package ru.dfsystems.spring.tutorial.dao;

import ru.dfsystems.spring.tutorial.generate.BaseJooq;

public interface BaseDao<Entity extends BaseJooq> {
    Entity create(Entity entity, Integer userId);

    Entity getActiveByIdd(Integer idd);

    void update(Entity room);
}
