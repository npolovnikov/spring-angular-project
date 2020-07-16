package ru.student.studentSpring.tutorial.service;

import ru.student.studentSpring.tutorial.dao.BaseDao;
import ru.student.studentSpring.tutorial.dao.BaseListDao;
import ru.student.studentSpring.tutorial.dto.BaseDto;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.generate.JooqBase;
import ru.student.studentSpring.tutorial.mapping.BaseMapping;

import java.time.LocalDateTime;

public abstract class BaseService<List extends BaseListDto, Dto extends BaseDto, Params, Entity extends JooqBase> {

    private BaseMapping mappingService;
    private BaseListDao<Entity, Params> listDao;
    private Class<List> listClass;
    private BaseDao<Entity> baseDao;
    private Class<Dto> dtoClass;
    private Class<Entity> entityClass;

    public BaseService(BaseMapping mappingService, BaseListDao<Entity, Params> listDao,
                       Class<List> listClass, BaseDao<Entity> baseDao, Class<Dto> dtoClass,
                       Class<Entity> entityClass) {
        this.mappingService = mappingService;
        this.listDao = listDao;
        this.listClass = listClass;
        this.baseDao = baseDao;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public Page<List> list(PageParams<Params> pageParams) {
        Page<Entity> page = listDao.list(pageParams);
        java.util.List<List> list = mappingService.mapList(page.getList(), listClass);
        return new Page<>(list, page.getTotalCount());
    }

    public void create(Dto dto) {

        baseDao.create(mappingService.map(dto, entityClass));
    }

    public Dto get(Integer idd) {
        return mappingService.map(baseDao.getActiveByIdd(idd), dtoClass);
    }

    public Dto update(Integer idd, Dto dto) {
        Entity entity = baseDao.getActiveByIdd(idd);
        if (entity == null) {
            throw new RuntimeException("");
        }
        entity.setDeleteDate(LocalDateTime.now());
        baseDao.update(entity);
        Entity newEntity = mappingService.map(dto, entityClass);
        newEntity.setIdd(entity.getIdd());
        baseDao.create(newEntity);
        return mappingService.map(entity, dtoClass);
    }

    public void delete(Integer idd) {
        Entity entity = baseDao.getActiveByIdd(idd);
        entity.setDeleteDate(LocalDateTime.now());
        baseDao.update(entity);
    }
}
