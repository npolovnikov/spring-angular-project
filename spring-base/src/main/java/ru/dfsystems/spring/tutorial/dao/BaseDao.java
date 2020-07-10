package ru.dfsystems.spring.tutorial.dao;

import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.generate.BaseJooq;

public interface BaseDao<Entity extends BaseJooq> {
    void create(Entity map);

    Entity getActiveByIdd(Integer idd);

    void update(Entity entity);
}
