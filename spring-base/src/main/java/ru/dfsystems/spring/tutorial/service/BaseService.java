package ru.dfsystems.spring.tutorial.service;

import ru.dfsystems.spring.tutorial.dao.BaseDao;
import ru.dfsystems.spring.tutorial.dao.BaseListDao;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.generate.BaseJooq;
import ru.dfsystems.spring.tutorial.mapping.BaseMapper;

import java.time.LocalDateTime;

public abstract class BaseService<List extends BaseListDto, Dto extends BaseDto, Params, Entity extends BaseJooq> {

    private BaseMapper<Entity, Dto, List> baseMapper;
    private BaseListDao<Entity, Params> listDao;
    private BaseDao<Entity> baseDao;

    public BaseService(BaseMapper <Entity, Dto, List> baseMapper,
                       BaseListDao<Entity, Params> listDao,
                       BaseDao<Entity> baseDao) {
        this.baseMapper = baseMapper;
        this.listDao = listDao;
        this.baseDao = baseDao;
    }

    public Page<List> list(PageParams<Params> pageParams) {
        Page<Entity> page = listDao.list(pageParams);
        java.util.List<List> list = baseMapper.entityListToDtoList(page.getList());
        return new Page<>(list, page.getTotalCount());
    }

    public void create(Dto dto) {
        baseDao.create(baseMapper.dtoToEntity(dto));
    }

    public Dto get(Integer idd) {
        return baseMapper.entityToDto(baseDao.getActiveByIdd(idd));
    }

    public Dto update(Integer idd, Dto dto) {
        Entity entity = baseDao.getActiveByIdd(idd);
        if (entity == null) {
            throw new RuntimeException("");
        }
        entity.setDeleteDate(LocalDateTime.now());
        baseDao.update(entity);

        Entity newEntity = baseMapper.dtoToEntity(dto);
        newEntity.setIdd(entity.getIdd());
        baseDao.create(newEntity);
        return baseMapper.entityToDto(newEntity);
    }

    public void delete(Integer idd) {
        Entity entity = baseDao.getActiveByIdd(idd);
        entity.setDeleteDate(LocalDateTime.now());
        baseDao.update(entity);
    }
}
