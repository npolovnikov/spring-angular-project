package ru.student.studentSpring.tutorial.mapping;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapping {
    <S, D> D map(S source, Class<D> clazz);

    <S, D> void map(S source, D dest);

    <S, D> List<D> mapList(List<S> sources, Class<D> clazz);
}
