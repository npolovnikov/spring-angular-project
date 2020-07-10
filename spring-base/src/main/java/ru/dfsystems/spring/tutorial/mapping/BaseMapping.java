package ru.dfsystems.spring.tutorial.mapping;

import java.util.List;

public interface BaseMapping {
    public <S, D> D map(S source, Class<D> clazz);

    public <S, D> void map(S source, D dest);

    public <S, D> List<D> mapList(List<S> source, Class<D> clazz);
}


