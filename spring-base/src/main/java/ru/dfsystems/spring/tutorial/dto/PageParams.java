package ru.dfsystems.spring.tutorial.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Параметры для page
 */
@Data
public class PageParams<T> implements Serializable {
    /**
     * С какого элемента выводить
     */
    private int start;
    /**
     * Количество элементов на одной странице
     */
    private int page;
    /**
     * Параметры для конкретной сущности (RoomParams etc)
     */
    private T params;
}
