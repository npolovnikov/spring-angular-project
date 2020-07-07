package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import ru.dfsystems.spring.tutorial.dto.BaseDto;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;
import ru.dfsystems.spring.tutorial.generate.BaseJooq;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
public interface BaseMapper<Entity extends BaseJooq, Dto extends BaseDto, DtoList extends BaseListDto> {

     DtoList entityToDtoList(Entity entity);

     Dto entityToDto(Entity entity);

     Entity dtoToEntity(Dto dto);

     default List<DtoList> entityListToDtoList(List<Entity> entities) {
         List<DtoList> dtoList = new ArrayList<>();
         for (Entity entity: entities) {
             dtoList.add(entityToDtoList(entity));
         }
         return dtoList;
     }

}
