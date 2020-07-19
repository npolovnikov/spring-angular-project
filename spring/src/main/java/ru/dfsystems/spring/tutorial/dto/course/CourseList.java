package ru.dfsystems.spring.tutorial.dto.course;

import lombok.Getter;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;


@Getter
@Setter
public class CourseList  extends BaseListDto {
    private String name;
    private String description;
    private Integer maxCo;
}
