package ru.dfsystems.spring.tutorial.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParams<T> implements Serializable {
    private int start; // since which object data will be outputted
    private int page; // till which
    private T params; // other parametrs
}
