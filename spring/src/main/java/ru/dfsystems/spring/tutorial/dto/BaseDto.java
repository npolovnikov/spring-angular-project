package ru.dfsystems.spring.tutorial.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class BaseDto <HistoryDto extends BaseHistoryDto> extends BaseListDto {
    private List<HistoryDto> history;
}
