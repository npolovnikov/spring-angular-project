package ru.student.studentSpring.tutorial.controller;

import org.springframework.web.bind.annotation.*;
import ru.student.studentSpring.tutorial.dto.BaseDto;
import ru.student.studentSpring.tutorial.dto.BaseListDto;
import ru.student.studentSpring.tutorial.dto.Page;
import ru.student.studentSpring.tutorial.dto.PageParams;
import ru.student.studentSpring.tutorial.generate.JooqBase;
import ru.student.studentSpring.tutorial.service.BaseService;

public abstract class BaseController<List extends BaseListDto, Dto extends BaseDto, Params, Entity extends JooqBase> {

    private BaseService<List, Dto, Params, Entity> service;

    public BaseController(BaseService<List, Dto, Params, Entity> service) {
        this.service = service;
    }

    @PostMapping("/list")
    public Page<List> getList(@RequestBody PageParams<Params> pageParams) {

        return service.list(pageParams);
    }

    @PostMapping
    public void create(@RequestBody Dto dto) {
        service.create(dto);
    }

    @GetMapping("/{idd}")
    public Dto get(@PathVariable(name = "idd") Integer idd) {

        return service.get(idd);
    }

    @PatchMapping("/{idd}")
    public Dto update(@PathVariable(name = "idd") Integer idd, @RequestBody Dto dto) {

        return service.update(idd, dto);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable(name = "idd") Integer idd) {
        service.delete(idd);
    }
}
