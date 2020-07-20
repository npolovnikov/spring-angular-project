package ru.dfsystems.spring.tutorial.controller;

import org.springframework.web.bind.annotation.*;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.dto.Page;
import ru.dfsystems.spring.tutorial.dto.PageParams;
import ru.dfsystems.spring.tutorial.generate.BaseJooq;
import ru.dfsystems.spring.tutorial.service.BaseService;

public abstract class BaseController<List extends BaseListDto, Dto extends BaseDto, Params, Entity extends BaseJooq> {
    private BaseService<List, Dto, Params, Entity> service;

    public BaseController(BaseService<List, Dto, Params, Entity> service) {
        this.service = service;
    }

    @PostMapping("/list")
    public Page<List> getList(@RequestBody PageParams<Params> pageParams) {
        return service.list(pageParams);
    }

    @PostMapping
    public abstract void create(@RequestBody Dto dto) throws Exception;

    @GetMapping("/{idd}")
    public Dto get(@PathVariable("idd") Integer idd) {
        return service.get(idd);
    }

    @PatchMapping("/{idd}")
    public abstract void update(@PathVariable("idd") Integer idd, @RequestBody Dto dto) throws Exception;

    @DeleteMapping("/{idd}")
    public abstract void delete(@PathVariable("idd") Integer idd) throws Exception;
}
