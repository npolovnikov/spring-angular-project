package ru.dfsystems.spring.tutorial.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Параметры для page
 */
@Data
public class PageParams<T> implements Serializable {
    /**
     * Начиная с какого элемента выводится на текущцю страницу
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
