package ru.dfsystems.spring.tutorial.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParams<T> implements Serializable {

    private Integer start;
    private Integer page;
    private  T params;
}
