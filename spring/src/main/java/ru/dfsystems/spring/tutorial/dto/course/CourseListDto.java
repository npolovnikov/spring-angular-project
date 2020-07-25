package ru.dfsystems.spring.tutorial.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dfsystems.spring.tutorial.dto.BaseListDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseListDto extends BaseListDto {
    private String name;
    private String description;
    private String teacherId;
    private String maxCountStudent;
//    private String startDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
