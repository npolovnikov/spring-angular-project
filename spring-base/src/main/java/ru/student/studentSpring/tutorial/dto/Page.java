package ru.student.studentSpring.tutorial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class Page<T>implements Serializable {
    public List<T> list;
    private Long totalCount;
}
