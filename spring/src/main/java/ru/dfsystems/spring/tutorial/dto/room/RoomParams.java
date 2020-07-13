package ru.dfsystems.spring.tutorial.dto.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Параметры для конкретной сущности (Room)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomParams {
    private Integer idd;
    private String number;
    private String block;
    /**
     * По какому полю сортируем
     */
    private String orderBy;
    /**
     * По возрастанию/убыванию (asc/desc)
     */
    private String orderDir;

    /**
     * Начальная дата для промежутка поиска
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDateStart;
    /**
     * Конечная дата для промежутка поиска
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDateEnd;
}
