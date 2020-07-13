package ru.dfsystems.spring.tutorial.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseListDto extends BaseListDto {
    private String name;
    private String description;
    private String teacherId;
    private String maxCountStudent;
    private String startDate;
    private String endDate;
}
