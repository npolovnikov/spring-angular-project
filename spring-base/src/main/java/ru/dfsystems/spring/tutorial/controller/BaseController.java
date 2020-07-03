package ru.dfsystems.spring.tutorial.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.generate.BaseJooq;
import ru.dfsystems.spring.tutorial.service.BaseService;

@Api( description = "Оперции с бизнес-объектами")
public abstract class BaseController<List extends BaseListDto, Dto extends BaseDto, Params, Entity extends BaseJooq> {
    private BaseService<List, Dto, Params, Entity> service;

    public BaseController(BaseService<List, Dto, Params, Entity> service) {
        this.service = service;
    }

    @PostMapping("/list")
    @ApiOperation(value = "Возвращает перечень объектов")
    public Page<List> getList(@RequestBody PageParams<Params> pageParams) {
        return service.list(pageParams);
    }

    @PostMapping
    @ApiOperation(value = "Создает объекты")
    public void create(@RequestBody Dto dto) {
        service.create(dto);
    }

    @GetMapping("/{idd}")
    @ApiOperation(value = "Возвращает объекты")
    public Dto get(@PathVariable("idd") Integer idd) {
        return service.get(idd);
    }

    @PatchMapping("/{idd}")
    @ApiOperation(value = "Изменяет объекты")
    public Dto update(@PathVariable("idd") Integer idd, @RequestBody Dto dto) {
        return service.update(idd, dto);
    }

    @DeleteMapping("/{idd}")
    @ApiOperation(value = "Удаляет объекты")
    public void delete(@PathVariable("idd") Integer idd) {
        service.delete(idd);
    }
}
